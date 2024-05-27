package com.sitaram.foodshare.features.dashboard.setting.manageAccount.domain

import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class ManageAccountUseCase(private val  manageAccountRepository: ManageAccountRepository) {
    operator fun invoke(email: String, password: String): Flow<Resource<ResponsePojo?>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(manageAccountRepository.getUpdatePassword(email, password)))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }
    operator fun invoke(email: String): Flow<Resource<ResponsePojo?>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data = manageAccountRepository.
            getDeleteAccount(email)))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }
}