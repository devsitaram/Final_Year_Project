package com.sitaram.foodshare.features.login.data.pojo


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Authentication(
    @SerializedName("access_token")
    var accessToken: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("user_id")
    var id: Int? = null,
    @SerializedName("profile")
    var profile: String? = null,
    @SerializedName("role")
    var role: String? = null,
    @SerializedName("username")
    var username: String? = null
)