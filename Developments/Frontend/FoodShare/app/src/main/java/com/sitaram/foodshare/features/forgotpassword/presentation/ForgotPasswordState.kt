package com.sitaram.foodshare.features.forgotpassword.presentation

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

data class ForgotPasswordState(
    val isLoading: Boolean = false,
    val data: ResponsePojo? = null,
    val isError: String? = null,
)