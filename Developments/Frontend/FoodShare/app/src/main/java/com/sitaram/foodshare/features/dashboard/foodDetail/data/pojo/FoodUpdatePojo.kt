package com.sitaram.foodshare.features.dashboard.foodDetail.data.pojo


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.sitaram.foodshare.features.dashboard.home.data.Food

@Keep
data class FoodUpdatePojo(
    @SerializedName("food")
    var food: Food? = null,
    @SerializedName("is_success")
    var isSuccess: Boolean? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Int? = null
)