package com.sitaram.foodshare.features.dashboard.profile.presentation

import android.os.Message
import com.sitaram.foodshare.features.dashboard.profile.data.ProfilePojo

data class ProfileState (
    val isLoading: Boolean = false,
    val data: ProfilePojo? = null,
    val error: String? = null,
    val message: String? = null
)