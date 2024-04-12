package com.sitaram.foodshare.features.dashboard.home.domain

import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.features.dashboard.home.data.FoodPojo
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.ProfileEntity
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.Exception

class HomeUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(): Flow<Resource<FoodPojo?>> = flow {
        emit(Resource.Loading())
        try {
            val result = homeRepository.getHomeFoodDetails()
            if (result?.isSuccess == true) {
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }

    operator fun invoke(id: Int?,  username: String?): Flow<Resource<ResponsePojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = homeRepository.getDeleteFood(id, username)
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception){
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }

    suspend operator fun invoke(profile: ProfileEntity) {
        try {
            homeRepository.saveUserProfile(profile)
        } catch (ex: Exception) {
            print(ex.message)
        }
    }

    suspend operator fun invoke(foods: FoodsEntity) {
        try {
            homeRepository.saveFoodDetails(food = foods)
        } catch (ex: Exception) {
            print(ex.message)
        }
    }

}