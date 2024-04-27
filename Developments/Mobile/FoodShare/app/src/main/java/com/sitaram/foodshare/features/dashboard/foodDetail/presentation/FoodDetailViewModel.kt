package com.sitaram.foodshare.features.dashboard.foodDetail.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationModelDAO
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.features.dashboard.foodDetail.domain.FoodDetailUseCase
import com.sitaram.foodshare.features.dashboard.localDatabase.domain.LocalDatabaseUseCase
import com.sitaram.foodshare.source.local.FoodsEntity
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
    var username: String? = null
    var email: String? = null
    var contactNumber: String? = null
    var photoUrl: String? = null

    private var _foodDetailState by mutableStateOf(FoodDetailState())
    val foodDetailState: FoodDetailState get() = _foodDetailState

    fun getFoodDetailState(foodId: Int) {
        this.foodId = foodId
        _foodDetailState = FoodDetailState(isLoading = true)
        localDatabase.invoke(foodId).onEach { result ->
            _foodDetailState = when (result) {
                is Resource.Loading -> {
                    FoodDetailState(isLoading = true)
                }

                is Resource.Success -> {
                    result.data.let {
                        username = it?.username
                        email = it?.email
                        contactNumber = it?.contactNumber
                        photoUrl = it?.photoUrl
                    }
                    FoodDetailState(data = result.data)
                }

                is Resource.Error -> {
                    FoodDetailState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUpdateDonateFood(foodId: Int?, donationModelDAO: DonationModelDAO?) {
        _foodDetailState = _foodDetailState.copy(isLoading = true)
        foodDetailUseCase.invoke(foodId, donationModelDAO).onEach { result ->
            _foodDetailState = when (result) {
                is Resource.Loading -> {
                    _foodDetailState.copy(isLoading = true)
                }

                is Resource.Success -> {
                    val foodsEntity = result.data?.food?.let { food ->
                        FoodsEntity(
                            id = food.id,
                            createdBy = food.createdBy,
                            createdDate = food.createdDate,
                            descriptions = food.descriptions,
                            foodTypes = food.foodTypes,
                            foodName = food.foodName,
                            isDelete = food.isDelete,
                            modifyBy = food.modifyBy,
                            modifyDate = food.modifyDate,
                            pickUpLocation = food.pickUpLocation,
                            expireTime = food.expireTime,
                            latitude = food.latitude?.toDouble(),
                            longitude = food.longitude?.toDouble(),
                            quantity = food.quantity,
                            status = food.status,
                            streamUrl = food.streamUrl,
                            userId = food.donor,
                            username = username,
                            email = email,
                            contactNumber = contactNumber,
                            photoUrl = photoUrl
                        )
                    }
                    _foodDetailState.copy(data = foodsEntity, isLoading = false, message = result.data?.message) // context.getString(R.string.updated_successful))
                }

                is Resource.Error -> {
                    _foodDetailState.copy(error = result.message, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUpdateDonateFoodImage(foodId: Int?, fileImage: File?) {
        _foodDetailState = _foodDetailState.copy(isLoading = true)
        foodDetailUseCase.invoke(foodId, fileImage).onEach { result ->
            _foodDetailState = when (result) {
                is Resource.Loading -> {
                    _foodDetailState.copy(isLoading = true)
                }

                is Resource.Success -> {
                    val foodsEntity = result.data?.food?.let { food ->
                        FoodsEntity(
                            id = food.id,
                            createdBy = food.createdBy,
                            createdDate = food.createdDate,
                            descriptions = food.descriptions,
                            foodTypes = food.foodTypes,
                            foodName = food.foodName,
                            isDelete = food.isDelete,
                            modifyBy = food.modifyBy,
                            modifyDate = food.modifyDate,
                            pickUpLocation = food.pickUpLocation,
                            expireTime = food.expireTime,
                            latitude = food.latitude?.toDouble(),
                            longitude = food.longitude?.toDouble(),
                            quantity = food.quantity,
                            status = food.status,
                            streamUrl = food.streamUrl,
                            userId = food.donor,
                            username = username,
                            email = email,
                            contactNumber = contactNumber,
                            photoUrl = photoUrl
                        )
                    }
                    _foodDetailState.copy(data = foodsEntity, isLoading = false, message = result.data?.message) // context.getString(R.string.updated_successful))
                }

                is Resource.Error -> {
                    _foodDetailState.copy(error = result.message, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSwipeToRefresh() {
        isRefreshing = true
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