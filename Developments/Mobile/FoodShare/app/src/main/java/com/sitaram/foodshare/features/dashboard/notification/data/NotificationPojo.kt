package com.sitaram.foodshare.features.dashboard.notification.data


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep

data class NotificationPojo(
    @SerializedName("is_success")
    var isSuccess: Boolean? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("notifications")
    var notifications: List<Notification?>? = null,
    @SerializedName("status")
    var status: Int? = null
)

@Keep

data class Notification(
    @SerializedName("created_by")
    var createdBy: String? = null,
    @SerializedName("created_date")
    var createdDate: String? = null,
    @SerializedName("descriptions")
    var descriptions: String? = null,
    @SerializedName("food")
    var food: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("is_delete")
    var isDelete: Boolean? = null,
    @SerializedName("title")
    var title: String? = null
)