package com.sitaram.foodshare.features.register.domain

import com.google.gson.annotations.SerializedName

data class RegisterModelDAO(
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("role")
    val role: String
)