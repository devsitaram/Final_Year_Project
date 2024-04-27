package com.sitaram.foodshare.features.register.data

import com.sitaram.foodshare.features.register.domain.RegisterModelDAO
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.features.register.domain.RegisterRepository

class RegisterRepositoryImpl(private val apiService: ApiService) : RegisterRepository {
    override suspend fun getRegisterUser(registerModelDAO: RegisterModelDAO?): RegisterPojo? {
           return apiService.registerUser(registerModelDAO)
    }
}