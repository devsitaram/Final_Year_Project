package com.sitaram.foodshare.features.dashboard.foodDetail.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationModelDAO
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.features.dashboard.foodDetail.domain.FoodDetailUseCase
import com.sitaram.foodshare.features.dashboard.foodDetail.domain.FoodModelDAO
import com.sitaram.foodshare.features.dashboard.localDatabase.domain.LocalDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val foodDetailUseCase: FoodDetailUseCase,
    private val localDatabase: LocalDatabaseUseCase,
) : ViewModel() {

    var isRefreshing by mutableStateOf(false)
    private var foodId: Int? = 0
    private var _foodDetailState by mutableStateOf(FoodDetailState())
    val foodDetailState: FoodDetailState get() = _foodDetailState

    // accept food
    private var _foodAcceptState by mutableStateOf(FoodAcceptState())
    val foodAcceptState: FoodAcceptState get() = _foodAcceptState

    fun getFoodDetailState(foodId: Int) {
        this.foodId = foodId
        _foodDetailState = FoodDetailState(isLoading = true)
        localDatabase.invoke(foodId).onEach { result ->
            _foodDetailState = when (result) {
                is Resource.Loading -> {
                    FoodDetailState(isLoading = true)
                }

                is Resource.Success -> {
                    FoodDetailState(data = result.data)
                }

                is Resource.Error -> {
                    FoodDetailState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAcceptFood(id: Int, status: String, userId: Int?, username: String?) {
        val foodModelDAO = FoodModelDAO(id, status.trim(), userId, username)
        foodDetailUseCase.invoke(foodModelDAO).onEach { result ->
            _foodAcceptState = when (result) {
                is Resource.Loading -> {
                    _foodAcceptState.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _foodAcceptState.copy(data = result.data, isLoading = false)
                }

                is Resource.Error -> {
                    _foodAcceptState.copy(error = result.message, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUpdateDonateFood(foodId: Int?, donationModelDAO: DonationModelDAO?, context: Context) {
        _foodDetailState = _foodDetailState.copy(isLoading = true)
        foodDetailUseCase.invoke(foodId, donationModelDAO).onEach { result ->
            _foodDetailState = when (result) {
                is Resource.Loading -> {
                    _foodDetailState.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _foodDetailState.copy(
                        data = result.data,
                        isLoading = false,
                        message = context.getString(R.string.updated_successful)
                    )
                }

                is Resource.Error -> {
                    _foodDetailState.copy(error = result.message, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUpdateDonateFoodImage(foodId: Int?, fileImage: File?, context: Context) {
        _foodDetailState = _foodDetailState.copy(isLoading = true)
        foodDetailUseCase.invoke(foodId, fileImage).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _foodDetailState = _foodDetailState.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _foodDetailState = _foodDetailState.copy(
                        data = result.data,
                        isLoading = false,
                        message = context.getString(R.string.updated_successful)
                    )

                }

                is Resource.Error -> {
                    _foodDetailState = _foodDetailState.copy(error = result.message, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSwipeToRefresh() {
        isRefreshing = true
        _foodDetailState = _foodDetailState.copy(isLoading = true)
        localDatabase.invoke(foodId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    isRefreshing = true
                }

                is Resource.Success -> {
                    _foodDetailState = _foodDetailState.copy(data = result.data)
                    isRefreshing = false
                }

                is Resource.Error -> {
                    _foodDetailState = _foodDetailState.copy(error = result.message)
                    isRefreshing = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun clearMessage() {
        _foodDetailState = _foodDetailState.copy(message = null)
    }
}