package com.sitaram.foodshare.features.dashboard.dashboardAdmin.history.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.history.domain.HistoryUseCase
import com.sitaram.foodshare.features.dashboard.localDatabase.domain.LocalDatabaseUseCase
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.local.ProfileEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val historyUseCase: HistoryUseCase, private val localDatabase: LocalDatabaseUseCase): ViewModel() {

    var isRefreshing by mutableStateOf(false)
    private var userId: Int? = null
    private var status: String? = null

    private var _historyState by mutableStateOf(HistoryState())
    val historyState: HistoryState get() = _historyState

    fun getFoodHistory(userId: Int?, status: String?) {
        this.userId = userId
        this.status = status
        _historyState = HistoryState(isLoading = true)
        historyUseCase.invoke(userId, status).onEach { result ->
            _historyState = when (result) {
                is Resource.Loading -> {
                    HistoryState(isLoading = true)
                }

                is Resource.Success -> {
                    HistoryState(data = result.data)
                }

                is Resource.Error -> {
                    HistoryState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSwipeToRefresh() {
        isRefreshing = true
        historyUseCase.invoke(userId, status).onEach { result ->
               when (result) {
                    is Resource.Loading -> {
                        isRefreshing = true
                    }

                    is Resource.Success -> {
                        _historyState = HistoryState(data = result.data)
                        isRefreshing = false
                    }

                    is Resource.Error -> {
                        _historyState = HistoryState(error = result.message)
                        isRefreshing = false
                    }
                }
        }.launchIn(viewModelScope)
    }

    // local database have save in foodDetails details
    fun saveUserProfile(profile: ProfileEntity) {
        viewModelScope.launch {
            localDatabase.invoke(profile)
        }
    }

    fun saveHistory(profile: HistoryEntity) {
        viewModelScope.launch {
            localDatabase.invoke(profile)
        }
    }

    fun saveFoodDetails(foods: FoodsEntity) {
        viewModelScope.launch {
            localDatabase.invoke(foods)
        }
    }
}