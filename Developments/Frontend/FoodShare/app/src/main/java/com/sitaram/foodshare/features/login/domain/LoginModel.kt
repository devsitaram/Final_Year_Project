package com.sitaram.foodshare.features.login.domain

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("password")
    val password: String? = null
)