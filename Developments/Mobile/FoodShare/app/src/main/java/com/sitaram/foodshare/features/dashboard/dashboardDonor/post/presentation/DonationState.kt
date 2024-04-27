package com.sitaram.foodshare.features.dashboard.dashboardDonor.post.presentation

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

data class DonationState(
    val isLoading: Boolean = false,
    val data: ResponsePojo? = null,
    val error: String? = null
)