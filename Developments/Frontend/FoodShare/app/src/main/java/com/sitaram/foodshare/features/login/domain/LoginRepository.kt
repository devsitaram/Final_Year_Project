package com.sitaram.foodshare.features.login.domain

import com.sitaram.foodshare.features.login.data.pojo.LoginAuthPojo

interface LoginRepository {
    suspend fun getLoginUserAuth(email: String, password: String): LoginAuthPojo?
}