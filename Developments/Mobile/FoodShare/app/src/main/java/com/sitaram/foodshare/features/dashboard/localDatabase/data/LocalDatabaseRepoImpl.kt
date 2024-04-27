package com.sitaram.foodshare.features.dashboard.localDatabase.data

import com.sitaram.foodshare.features.dashboard.localDatabase.domain.LocalDatabaseRepository
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.local.NotificationEntity
import com.sitaram.foodshare.source.local.ProfileEntity
import com.sitaram.foodshare.source.local.RoomDao

class LocalDatabaseRepoImpl(private val roomDao: RoomDao): LocalDatabaseRepository {

    override suspend fun saveFoodDetails(food: FoodsEntity) {
        roomDao.insertFoodDetails(food)
    }

    override suspend fun saveUserProfile(profile: ProfileEntity) {
        roomDao.insertUserProfile(profile)
    }

    override suspend fun saveHistoryDetail(history: HistoryEntity) {
        roomDao.insertFoodHistory(history)
    }

    override suspend fun getFoodDetailById(foodId: Int?): FoodsEntity? {
        return roomDao.getFoodDetailsById(foodId)
    }

    override suspend fun getAllNotification(): List<NotificationEntity>? {
        return roomDao.getAllNotifications()
    }

    override suspend fun saveNotificationDetail(notification: NotificationEntity) {
        roomDao.insertNotification(notification)
    }
}