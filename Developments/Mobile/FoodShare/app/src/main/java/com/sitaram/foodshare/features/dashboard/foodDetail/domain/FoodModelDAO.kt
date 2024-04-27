package com.sitaram.foodshare.features.dashboard.foodDetail.domain


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class FoodModelDAO(
    @SerializedName("food_id")
    var foodId: Int? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("user_id")
    var userId: Int? = null,
    @SerializedName("username")
    var username: String? = null
)