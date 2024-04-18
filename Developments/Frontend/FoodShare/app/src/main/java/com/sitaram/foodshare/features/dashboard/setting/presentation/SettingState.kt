package com.sitaram.foodshare.features.dashboard.setting.presentation

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

data class SettingState(
    val isLoading: Boolean = false,
    val data: ResponsePojo? = null,
    val error: String? = null,
    val message: String? = null
)
