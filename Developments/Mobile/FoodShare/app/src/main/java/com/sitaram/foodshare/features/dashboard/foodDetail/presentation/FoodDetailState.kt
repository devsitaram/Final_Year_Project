package com.sitaram.foodshare.features.dashboard.foodDetail.presentation

import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

data class FoodDetailState(
    val isLoading: Boolean = false,
    val data: FoodsEntity? = null,
    val error: String? = null,
    val message: String? = null,
)

data class FoodAcceptState(
    val isLoading: Boolean = false,
    val data: ResponsePojo? = null,
    val error: String? = null,
    val message: String? = null
)