package com.sitaram.foodshare.features.login.presentation

import com.sitaram.foodshare.features.login.data.pojo.LoginAuthPojo

data class LogInState(
    val isLoading: Boolean = false,
    val data: LoginAuthPojo? = null,
    val error: String? = null
)