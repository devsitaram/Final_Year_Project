package com.sitaram.foodshare.features.dashboard.localDatabase.data

import com.sitaram.foodshare.features.dashboard.localDatabase.domain.LocalDatabaseRepository
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.local.NotificationEntity
import com.sitaram.foodshare.source.local.ProfileEntity
import com.sitaram.foodshare.source.local.RoomDao
import com.sitaram.foodshare.source.remote.api.ApiService

class LocalDatabaseRepoImpl(private val roomDao: RoomDao, private val apiService: ApiService): LocalDatabaseRepository {

    override suspend fun saveFoodDetails(food: FoodsEntity) {
        roomDao.insertFoodDetails(food)
    }

    override suspend fun saveUserProfile(profile: ProfileEntity) {
        roomDao.insertUserProfile(profile)
    }

    override suspend fun saveHistoryDetail(history: HistoryEntity) {
        roomDao.insertFoodHistory(history)
    }

    override suspend fun saveNotificationDetail(notification: NotificationEntity) {
        roomDao.insertNotification(notification)
    }

//    override suspend fun getFoodDetailById(foodId: Int?): FoodsEntity? {
//        return roomDao.getFoodDetailsById(foodId)
//    }

    override suspend fun getAllNotification(): List<NotificationEntity>? {
        return roomDao.getAllNotifications()
    }

    override suspend fun getFoodDetailById(foodId: Int): FoodsEntity? {
        val result = roomDao.getFoodDetailsById(foodId)
        if (result == null) {
            val response = apiService.getFoodDetails(foodId)
            if (response?.isSuccess == true) {
                return response.foodDetail.let {
                    FoodsEntity(
                        id = it?.id,
                        createdBy = it?.createdBy,
                        createdDate = it?.createdDate,
                        descriptions = it?.descriptions,
                        foodTypes = it?.foodTypes,
                        foodName = it?.foodName,
                        isDelete = it?.isDelete,
                        modifyBy = it?.modifyBy,
                        modifyDate = it?.modifyDate,
                        pickUpLocation = it?.pickUpLocation,
                        expireTime = it?.expireTime,
                        latitude = it?.latitude?.toDouble(),
                        longitude = it?.longitude?.toDouble(),
                        quantity = it?.quantity,
                        status = it?.status,
                        streamUrl = it?.streamUrl,
                        userId = it?.user?.id,
                        username = it?.user?.username,
                        email = it?.user?.email,
                        contactNumber = it?.user?.contactNumber,
                        photoUrl = it?.user?.photoUrl
                    )
                }
            } else {
                return roomDao.getFoodDetailsById(foodId)
            }
        } else {
            return result
        }
    }
}