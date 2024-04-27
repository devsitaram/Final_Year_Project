package com.sitaram.foodshare.features.dashboard.profile.domain

import com.sitaram.foodshare.features.dashboard.profile.data.ProfilePojo
import com.sitaram.foodshare.helper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.lang.Exception

class ProfileUseCase(private val profileRepository: ProfileRepository) {

    // get profile details
    operator fun invoke(): Flow<Resource<ProfilePojo?>> = flow {
        emit(Resource.Loading())
        try {
            val result = profileRepository.getUserProfile()
            if (result?.isSuccess == true) {
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }

    // update profile details
    operator fun invoke(userId: Int?, updateProfile: ProfileModelDAO?): Flow<Resource<ProfilePojo?>> = flow {
        emit(Resource.Loading())
        try {
            val result = profileRepository.updateProfile(userId, updateProfile)
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message  = result?.message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }

    operator fun invoke(userId: Int?, imageFile: File?): Flow<Resource<ProfilePojo?>> = flow {
        emit(Resource.Loading())
        try {
            val result = profileRepository.updateProfilePicture(userId, imageFile)
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message  = result?.message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }
}