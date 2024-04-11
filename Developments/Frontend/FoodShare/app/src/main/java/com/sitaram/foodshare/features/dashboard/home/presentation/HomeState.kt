package com.sitaram.foodshare.features.dashboard.home.presentation

import com.sitaram.foodshare.features.dashboard.home.data.FoodPojo
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

data class HomeState(
    val isLoading: Boolean = false,
    val data: FoodPojo? = null,
    val error: String? = null,
    val message: String? = null
)

data class DeleteState(
    val isLoading: Boolean = false,
    val data: ResponsePojo? = null,
    val error: String? = null,
    val message: String? = null
)
