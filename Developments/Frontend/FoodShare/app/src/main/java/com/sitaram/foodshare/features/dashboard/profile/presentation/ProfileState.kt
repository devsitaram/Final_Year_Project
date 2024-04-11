package com.sitaram.foodshare.features.dashboard.profile.presentation

import com.sitaram.foodshare.features.dashboard.profile.data.ProfilePojo

class ProfileState (
    val isLoading: Boolean = false,
    val isProgress: Boolean = false,
    val data: ProfilePojo? = null,
    val error: String? = null
)