package com.sitaram.foodshare.features.forgotpassword.data

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.features.forgotpassword.domain.ForgotPasswordDAO
import com.sitaram.foodshare.features.forgotpassword.domain.ForgotPasswordRepository

class ForgotPasswordRepositoryImpl(private val apiService: ApiService) : ForgotPasswordRepository {
    override suspend fun getVerifyEmail(email: String): ResponsePojo? {
        return apiService.getVerifyEmail(email= email)
    }

    override suspend fun getUpdatePassword(email: String?, password: String?): ResponsePojo? {
        return apiService.forgotPassword(ForgotPasswordDAO(email = email, newPassword = password))
    }
}