package com.sitaram.foodshare.features.dashboard.setting.data

import com.sitaram.foodshare.features.dashboard.setting.domain.SettingRepository
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

class SettingRepoImpl(private val apiService: ApiService): SettingRepository {
    override suspend fun getLogOut(): ResponsePojo? {
        return apiService.getLogOut()
    }
}