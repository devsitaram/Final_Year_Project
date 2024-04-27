package com.sitaram.foodshare.features.register.data


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep

data class RegisterPojo(
    @SerializedName("is_success")
    var isSuccess: Boolean? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Int? = null,
    @SerializedName("system_token")
    var systemToken: String? = null
)