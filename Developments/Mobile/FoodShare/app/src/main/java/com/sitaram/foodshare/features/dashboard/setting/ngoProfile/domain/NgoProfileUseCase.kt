package com.sitaram.foodshare.features.dashboard.setting.ngoProfile.domain

import com.sitaram.foodshare.features.dashboard.setting.ngoProfile.data.NgoProfilePojo
import com.sitaram.foodshare.features.dashboard.setting.ngoProfile.data.NumberOfDataPojo
import com.sitaram.foodshare.helper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class NgoProfileUseCase(private val ngoProfileRepository: NgoProfileRepository) {

    operator fun invoke(): Flow<Resource<NgoProfilePojo?>> = flow {
        emit(Resource.Loading())
        try {
            val result = ngoProfileRepository.getNgoProfile()
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (ex: Exception){
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }

    operator fun invoke(role: String?): Flow<Resource<NumberOfDataPojo?>> = flow {
        emit(Resource.Loading())
        try {
            val result = ngoProfileRepository.getNumberOfData(role)
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (ex: Exception){
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }
}