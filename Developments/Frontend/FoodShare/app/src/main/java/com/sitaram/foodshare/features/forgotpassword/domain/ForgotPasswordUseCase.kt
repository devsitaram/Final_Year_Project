package com.sitaram.foodshare.features.forgotpassword.domain

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import com.sitaram.foodshare.helper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class ForgotPasswordUseCase(private val forgotPasswordRepository: ForgotPasswordRepository) {

    operator fun invoke(email: String): Flow<Resource<ResponsePojo?>> = flow {
        emit(Resource.Loading())
        try {
            val result = forgotPasswordRepository.getVerifyEmail(email)
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "Not found!"))
        }
    }

    operator fun invoke(email: String?, password: String?): Flow<Resource<ResponsePojo?>> = flow {
        emit(Resource.Loading())
        try {
            val result = forgotPasswordRepository.getUpdatePassword(email, password)
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "Not found!"))
        }
    }
}