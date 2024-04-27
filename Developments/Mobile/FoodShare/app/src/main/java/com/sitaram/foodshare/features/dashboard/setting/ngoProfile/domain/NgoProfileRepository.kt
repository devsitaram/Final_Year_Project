package com.sitaram.foodshare.features.dashboard.setting.ngoProfile.domain

import com.sitaram.foodshare.features.dashboard.setting.ngoProfile.data.NgoProfilePojo
import com.sitaram.foodshare.features.dashboard.setting.ngoProfile.data.NumberOfDataPojo

interface NgoProfileRepository {

    suspend fun getNgoProfile(): NgoProfilePojo?

    suspend fun getNumberOfData(role: String?): NumberOfDataPojo?
}