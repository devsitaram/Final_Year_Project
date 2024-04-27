package com.sitaram.foodshare.features.dashboard.setting.ngoProfile.data


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class NumberOfDataPojo(
    @SerializedName("data")
    var data: NumberOfData? = null,
    @SerializedName("is_success")
    var isSuccess: Boolean? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Int? = null
)

@Keep
data class NumberOfData(
    @SerializedName("food")
    var food: Int? = null,
    @SerializedName("history")
    var history: Int? = null,
    @SerializedName("report")
    var report: Int? = null,
    @SerializedName("user")
    var user: Int? = null,
    @SerializedName("device")
    var device: Int? = null
)