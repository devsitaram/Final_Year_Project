package com.sitaram.foodshare.features.login.data.pojo


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LoginAuthPojo(
    @SerializedName("auth")
    var authentication: Authentication? = null,
    @SerializedName("is_success")
    var isSuccess: Boolean? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Int? = null
)