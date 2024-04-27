package com.sitaram.foodshare.features.dashboard.pushNotification

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FcmDeviceToken(
    @SerializedName("fcm_device_token")
    var fcmDeviceToken: String? = null,
    @SerializedName("id")
    var id: Int? = null,
)
