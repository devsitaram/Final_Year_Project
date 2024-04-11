package com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.data.pojo


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ReportPojo(
    @SerializedName("data")
    var data: List<Data?>? = null,
    @SerializedName("is_success")
    var isSuccess: Boolean? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Int? = null
)