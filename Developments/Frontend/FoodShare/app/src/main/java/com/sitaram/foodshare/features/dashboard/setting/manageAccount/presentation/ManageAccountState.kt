package com.sitaram.foodshare.features.dashboard.setting.manageAccount.presentation

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

class UpdateAccountState (
    val isLoading: Boolean = false,
    val data: ResponsePojo? = null,
    val isError: String? = null
)

class DeleteAccountState (
    val isLoading: Boolean = false,
    val data: ResponsePojo? = null,
    val isError: String? = null
)