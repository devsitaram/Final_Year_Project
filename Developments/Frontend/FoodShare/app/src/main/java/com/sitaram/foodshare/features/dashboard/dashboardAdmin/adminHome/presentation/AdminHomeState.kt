package com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.presentation

import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.data.pojo.ReportPojo

data class AdminHomeState(
    val isLoading: Boolean = false,
    val data: ReportPojo? = null,
    val error: String? = null,
    val message: String? = null
)
