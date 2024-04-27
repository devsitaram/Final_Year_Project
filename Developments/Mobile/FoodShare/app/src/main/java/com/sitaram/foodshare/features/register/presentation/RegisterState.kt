package com.sitaram.foodshare.features.register.presentation

import com.sitaram.foodshare.features.register.data.RegisterPojo

class RegisterState (
    val isLoading: Boolean = false,
    val data: RegisterPojo? = null,
    val error: String? = null
)