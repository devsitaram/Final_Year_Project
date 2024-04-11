package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.data.pojo


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PendingPojo(
    @SerializedName("history")
    var history: List<History?>? = null,
    @SerializedName("is_success")
    var isSuccess: Boolean? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Int? = null,
)