package com.sitaram.foodshare.features.login.data

import com.sitaram.foodshare.features.login.data.pojo.LoginAuthPojo
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.features.login.domain.LoginModel
import com.sitaram.foodshare.features.login.domain.LoginRepository

class LoginRepositoryImpl(private val apiService: ApiService) : LoginRepository {

    // login authentications
    override suspend fun getLoginUserAuth(email: String, password: String): LoginAuthPojo? {
            val loginModel = LoginModel(email = email, password = password)
            return apiService.getLoginUserAuth(loginModel)
    }
}