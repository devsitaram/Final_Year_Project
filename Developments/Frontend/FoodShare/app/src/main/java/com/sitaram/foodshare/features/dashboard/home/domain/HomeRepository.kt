package com.sitaram.foodshare.features.dashboard.home.domain

import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.ProfileEntity
import com.sitaram.foodshare.features.dashboard.home.data.FoodPojo
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

interface HomeRepository {
    suspend fun getHomeFoodDetails(): FoodPojo?
    suspend fun saveFoodDetails(food: FoodsEntity)
    suspend fun saveUserProfile(profile: ProfileEntity)
    suspend fun getDeleteFood(id: Int?, username: String?): ResponsePojo?
}