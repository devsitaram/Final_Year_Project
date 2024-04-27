package com.sitaram.foodshare.features.login.data

import com.sitaram.foodshare.features.login.data.pojo.LoginAuthPojo
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.features.login.domain.LoginModelDTO
import com.sitaram.foodshare.features.login.domain.LoginRepository
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

class LoginRepositoryImpl(private val apiService: ApiService) : LoginRepository {

    // login authentications
    override suspend fun getLoginUserAuth(email: String, password: String): LoginAuthPojo? {
            val loginModel = LoginModelDTO(email = email, password = password)
            return apiService.getLoginUserAuth(loginModel)
    }

    override suspend fun setDeviceFcmTokenSave(userId: Int?, fcmToken: String?, userName: String?): ResponsePojo? {
        return apiService.deviceFcmTokenSave(userId, fcmToken, userName)
    }
}