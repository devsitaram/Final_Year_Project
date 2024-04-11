package com.sitaram.foodshare.features.dashboard.setting.manageAccount.domain

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

interface ManageAccountRepository {
    suspend fun getUpdatePassword(email: String, newPassword: String): ResponsePojo?
    suspend fun getDeleteAccount(email: String): ResponsePojo?
}