package com.sitaram.foodshare.features.dashboard.setting.domain

import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SettingUseCase(private val settingRepository: SettingRepository) {

    operator fun invoke(): Flow<Resource<ResponsePojo?>> = flow {
        emit(Resource.Loading())
        try {
            val result = settingRepository.getLogOut()
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception){
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }
}