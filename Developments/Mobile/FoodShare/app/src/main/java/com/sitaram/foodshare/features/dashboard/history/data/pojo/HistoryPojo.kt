package com.sitaram.foodshare.features.dashboard.history.data.pojo


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class FoodHistoryPojo(
    @SerializedName("foods")
    var foods: List<History>? = listOf(),
    @SerializedName("is_success")
    var isSuccess: Boolean? = false,
    @SerializedName("message")
    var message: String? = "",
    @SerializedName("status")
    var status: Int? = 0
)