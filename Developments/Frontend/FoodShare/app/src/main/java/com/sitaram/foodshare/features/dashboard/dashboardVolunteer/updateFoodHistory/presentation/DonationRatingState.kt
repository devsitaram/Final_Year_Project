package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.presentation

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

data class DonationRatingState(
    val isLoading: Boolean = false,
    val data: ResponsePojo? = null,
    val error: String? = null,
    val message: String? = null
)