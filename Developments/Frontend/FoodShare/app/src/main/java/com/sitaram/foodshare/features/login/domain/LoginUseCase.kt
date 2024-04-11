package com.sitaram.foodshare.features.login.domain

import com.sitaram.foodshare.features.login.data.pojo.LoginAuthPojo
import com.sitaram.foodshare.helper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class LoginUseCase(private val loginRepository: LoginRepository) {
    operator fun invoke(email: String, password: String): Flow<Resource<LoginAuthPojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = loginRepository.getLoginUserAuth(email, password)
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