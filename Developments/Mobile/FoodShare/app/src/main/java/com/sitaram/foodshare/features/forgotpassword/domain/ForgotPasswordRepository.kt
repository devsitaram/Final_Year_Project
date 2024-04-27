package com.sitaram.foodshare.features.forgotpassword.domain

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

interface ForgotPasswordRepository {
    suspend fun getVerifyEmail(email: String): ResponsePojo?
    suspend fun getUpdatePassword(email: String?, password: String?): ResponsePojo?
}