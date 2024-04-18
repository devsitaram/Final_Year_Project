package com.sitaram.foodshare.features.dashboard.setting.manageAccount.presentation

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

data class UpdateAccountState (
    val isLoading: Boolean = false,
    val data: ResponsePojo? = null,
    val isError: String? = null
)

data class DeleteAccountState (
    val isLoading: Boolean = false,
    val data: ResponsePojo? = null,
    val isError: String? = null
)