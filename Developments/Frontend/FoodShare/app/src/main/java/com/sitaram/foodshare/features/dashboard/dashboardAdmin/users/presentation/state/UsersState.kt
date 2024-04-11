package com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.presentation.state

import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data.UsersPojo

data class UsersState(
    val isLoading: Boolean = false,
    val data: UsersPojo? = null,
    val error: String? = null
)