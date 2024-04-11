package com.sitaram.foodshare.features.dashboard.userProfileDetails.presentation

import com.sitaram.foodshare.source.local.ProfileEntity

class UserDetailState (
    val isLoading: Boolean = false,
    val data: ProfileEntity? = null,
    val error: String? = null
)