package com.sitaram.foodshare.features.login.domain

import com.sitaram.foodshare.features.login.data.pojo.LoginAuthPojo
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

interface LoginRepository {
    suspend fun getLoginUserAuth(email: String, password: String): LoginAuthPojo?
    suspend fun setDeviceFcmTokenSave(userId: Int?, fcmToken: String?, userName: String?): ResponsePojo?
}