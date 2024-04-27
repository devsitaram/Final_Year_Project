package com.sitaram.foodshare.features.dashboard.setting.ngoProfile.presentation

import com.sitaram.foodshare.features.dashboard.setting.ngoProfile.data.NgoProfilePojo
import com.sitaram.foodshare.features.dashboard.setting.ngoProfile.data.NumberOfDataPojo

data class NgoProfileState(
    val isLoading: Boolean = false,
    val data: NgoProfilePojo? = null,
    val error: String? = null,
    val message: String? = null
)

data class NumberOfDataState(
    val isLoading: Boolean = false,
    val data: NumberOfDataPojo? = null,
    val error: String? = null,
    val message: String? = null
)