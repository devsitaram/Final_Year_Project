package com.sitaram.foodshare.features.dashboard.setting.manageAccount.data

import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import com.sitaram.foodshare.features.dashboard.setting.manageAccount.domain.DeleteAccount
import com.sitaram.foodshare.features.dashboard.setting.manageAccount.domain.ManageAccountRepository
import com.sitaram.foodshare.features.dashboard.setting.manageAccount.domain.UpdatePassword

class ManageAccountRepositoryImpl(private val apiService: ApiService):
    ManageAccountRepository {

    override suspend fun getUpdatePassword(email: String, newPassword: String): ResponsePojo? {
        return apiService.updatePassword(UpdatePassword(email = email, newPassword = newPassword))
    }

    override suspend fun getDeleteAccount(email: String): ResponsePojo? {
        return apiService.deleteAccount(DeleteAccount(email = email, updateQuery = true))
    }
}