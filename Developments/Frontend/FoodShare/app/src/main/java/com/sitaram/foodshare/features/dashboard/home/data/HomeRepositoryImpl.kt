package com.sitaram.foodshare.features.dashboard.home.data

import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.ProfileEntity
import com.sitaram.foodshare.source.local.RoomDao
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.features.dashboard.home.domain.HomeRepository
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

class HomeRepositoryImpl(private val apiService: ApiService, private val roomDao: RoomDao) : HomeRepository {
    override suspend fun getHomeFoodDetails(): FoodPojo? {
        return apiService.getNewFoodDetails()
    }

    override suspend fun saveFoodDetails(food: FoodsEntity) {
        roomDao.insertFoodDetails(food)
    }

    override suspend fun saveUserProfile(profile: ProfileEntity) {
        roomDao.insertUserProfile(profile)
    }

    override suspend fun getDeleteFood(id: Int?,  username: String?): ResponsePojo? {
        return apiService.getDeleteFood(id, username)
    }
}