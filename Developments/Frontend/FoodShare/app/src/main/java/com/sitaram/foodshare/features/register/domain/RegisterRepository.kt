package com.sitaram.foodshare.features.register.domain

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

interface RegisterRepository {
    suspend fun getRegisterUser(registerModelDAO: RegisterModelDAO?): ResponsePojo?
}