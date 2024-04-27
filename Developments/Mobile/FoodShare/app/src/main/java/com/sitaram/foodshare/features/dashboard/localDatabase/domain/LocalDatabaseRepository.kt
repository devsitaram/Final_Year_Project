package com.sitaram.foodshare.features.dashboard.localDatabase.domain

import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.local.NotificationEntity
import com.sitaram.foodshare.source.local.ProfileEntity

interface LocalDatabaseRepository {
    suspend fun saveFoodDetails(food: FoodsEntity)
    suspend fun saveUserProfile(profile: ProfileEntity)
    suspend fun saveHistoryDetail(history: HistoryEntity)
    suspend fun getFoodDetailById(foodId: Int?): FoodsEntity?

    suspend fun getAllNotification(): List<NotificationEntity>?
    suspend fun saveNotificationDetail(notification: NotificationEntity)
}