package com.sitaram.foodshare.features.dashboard.home.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.ProfileEntity
import com.sitaram.foodshare.features.dashboard.home.domain.HomeUseCase
import com.sitaram.foodshare.features.dashboard.localDatabase.domain.LocalDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val localDatabase: LocalDatabaseUseCase
) : ViewModel() {

    var isRefreshing by mutableStateOf(false)

    private var _homeState by mutableStateOf(HomeState())
    val homeState: HomeState get() = _homeState

    // Delete state
    private var _deleteState by mutableStateOf(DeleteState())
    val deleteState: DeleteState get() = _deleteState

    init {
        getFoodDetails()
    }

    private fun getFoodDetails() {
        homeUseCase.invoke().onEach { result ->
            _homeState = when (result) {
                is Resource.Loading -> {
                    HomeState(isLoading = true)
                }

                is Resource.Success -> {
                    HomeState(data = result.data)
                }

                is Resource.Error -> {
                    HomeState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSwipeToRefresh() {
        isRefreshing = true
        homeUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    isRefreshing = true
                }

                is Resource.Success -> {
                    _homeState = HomeState(data = result.data)
                    isRefreshing = false
                }

                is Resource.Error -> {
                    _homeState = HomeState(error = result.message)
                    isRefreshing = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getDeleteFood(id: Int?, username: String?){
        _deleteState = DeleteState(isLoading = true)
        val updateList = _homeState.data?.foods?.toMutableList() // Copy the list
        updateList?.removeIf { it?.id == id } // Remove the food item with matching ID
        homeUseCase.invoke(id, username).onEach { result ->
            _deleteState = when (result) {
                is Resource.Loading -> {
                    DeleteState(isLoading = true)
                }

                is Resource.Success -> {
                    _homeState = _homeState.copy(data = _homeState.data?.copy(foods = updateList), message = result.message, isLoading = false)
                    DeleteState(message = result.data?.message)
                }

                is Resource.Error -> {
                    DeleteState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    // save the user profile details in local database
    suspend fun saveUserProfile(profile: ProfileEntity) {
        viewModelScope.launch {
            localDatabase.invoke(profile)
        }
    }

    // local database have save in foodDetails details
    suspend fun saveFoodDetails(foods: FoodsEntity) {
        viewModelScope.launch {
            localDatabase.invoke(foods)
        }
    }

    fun clearMessage() {
        _deleteState = _deleteState.copy(message = null)
    }
}