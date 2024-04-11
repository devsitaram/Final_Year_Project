package com.sitaram.foodshare.features.register.presentation

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

class RegisterState (
    val isLoading: Boolean = false,
    val data: ResponsePojo? = null,
    val isError: String? = ""
)