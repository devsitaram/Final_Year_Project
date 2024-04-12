package com.sitaram.foodshare.features.register.domain

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import com.sitaram.foodshare.helper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class RegisterUseCase(private val registerRepository: RegisterRepository) {
    operator fun invoke(registerModelDAO: RegisterModelDAO?): Flow<Resource<ResponsePojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = registerRepository.getRegisterUser(registerModelDAO)
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }
}