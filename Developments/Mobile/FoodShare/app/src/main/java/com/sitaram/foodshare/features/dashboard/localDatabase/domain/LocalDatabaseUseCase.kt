package com.sitaram.foodshare.features.dashboard.localDatabase.domain

import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.local.NotificationEntity
import com.sitaram.foodshare.source.local.ProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalDatabaseUseCase(private val localDatabaseRepository: LocalDatabaseRepository) {

    suspend operator fun invoke(foods: FoodsEntity) {
        try {
            localDatabaseRepository.saveFoodDetails(food = foods)
        } catch (ex: Exception) {
            print(ex.message)
        }
    }

    suspend operator fun invoke(profile: ProfileEntity) {
        try {
            localDatabaseRepository.saveUserProfile(profile = profile)
        } catch (ex: Exception) {
            print(ex.message)
        }
    }

    suspend operator fun invoke(history: HistoryEntity) {
        try {
            localDatabaseRepository.saveHistoryDetail(history)
        } catch (ex: java.lang.Exception) {
            print(ex.message)
        }
    }

    suspend operator fun invoke(notification: NotificationEntity) {
        try {
            localDatabaseRepository.saveNotificationDetail(notification)
        } catch (ex: java.lang.Exception) {
            print(ex.message)
        }
    }

    operator fun invoke():  Flow<Resource<List<NotificationEntity>?>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data = localDatabaseRepository.getAllNotification()))
        } catch (ex: Exception) {
            emit(Resource.Error(message =  "Unable to connect to the server."))
        }
    }

    // Get Food Details
    operator fun invoke(foodId: Int): Flow<Resource<FoodsEntity?>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data = localDatabaseRepository.getFoodDetailById(foodId)))
        } catch (ex: Exception) {
            emit(Resource.Error(message =  "Unable to connect to the server."))
        }
    }
}