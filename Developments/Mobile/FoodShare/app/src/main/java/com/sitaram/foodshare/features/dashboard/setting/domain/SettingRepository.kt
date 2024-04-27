package com.sitaram.foodshare.features.dashboard.setting.domain

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

interface SettingRepository {
    suspend fun getLogOut(): ResponsePojo?
}