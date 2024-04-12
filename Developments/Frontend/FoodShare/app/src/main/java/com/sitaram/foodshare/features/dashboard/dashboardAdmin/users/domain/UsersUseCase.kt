package com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.domain

import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data.UsersPojo
import com.sitaram.foodshare.helper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UsersUseCase(private val usersRepository: UsersRepository) {
    operator fun invoke(userId: Int?, isVerify: Boolean?): Flow<Resource<UsersPojo?>> = flow {
        emit(Resource.Loading())
        try {
            val result = usersRepository.isAccountVerify(userId = userId, isVerify = isVerify)
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message =  "Unable to connect to the server."))
        }
    }

    operator fun invoke(): Flow<Resource<UsersPojo?>> = flow {
        emit(Resource.Loading())
        try {
            val result = usersRepository.getAllTypesOfUsers()
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message =  "Unable to connect to the server."))
        }
    }
}