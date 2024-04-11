package com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.presentation

import com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.data.DonorHistoryPojo

data class DonorHistoryState(
    val isLoading: Boolean = false,
    val data: DonorHistoryPojo? = null,
    val error: String? = null,
    val message: String? = null
)
