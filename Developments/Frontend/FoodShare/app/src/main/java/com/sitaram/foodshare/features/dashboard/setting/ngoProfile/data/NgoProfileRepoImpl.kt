package com.sitaram.foodshare.features.dashboard.setting.ngoProfile.data

import com.sitaram.foodshare.features.dashboard.setting.ngoProfile.domain.NgoProfileRepository
import com.sitaram.foodshare.source.remote.api.ApiService

class NgoProfileRepoImpl(private val apiService: ApiService): NgoProfileRepository {
    override suspend fun getNgoProfile(): NgoProfilePojo? {
        return apiService.getNgoProfile()
    }

    override suspend fun getNumberOfData(role: String?): NumberOfDataPojo? {
        return apiService.getNumberOfData(role)
    }
}