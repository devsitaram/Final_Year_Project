package com.sitaram.foodshare.features.dashboard.userProfileDetails.domain

import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.local.ProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserDetailUseCase(private val userDetailRepository: UserDetailRepository) {
    operator fun invoke(userId: Int): Flow<Resource<ProfileEntity?>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data = userDetailRepository.getUserProfileById(userId = userId)))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Not found!"))
        }
    }
}