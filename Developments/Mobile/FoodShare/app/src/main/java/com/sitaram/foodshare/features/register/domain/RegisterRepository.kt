package com.sitaram.foodshare.features.register.domain

import com.sitaram.foodshare.features.register.data.RegisterPojo

interface RegisterRepository {
    suspend fun getRegisterUser(registerModelDAO: RegisterModelDAO?): RegisterPojo?
}