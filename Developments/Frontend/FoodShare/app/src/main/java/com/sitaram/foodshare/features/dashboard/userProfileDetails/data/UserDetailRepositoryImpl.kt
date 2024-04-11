package com.sitaram.foodshare.features.dashboard.userProfileDetails.data

import com.sitaram.foodshare.source.local.ProfileEntity
import com.sitaram.foodshare.source.local.RoomDao
import com.sitaram.foodshare.features.dashboard.userProfileDetails.domain.UserDetailRepository

class UserDetailRepositoryImpl(private val roomDao: RoomDao): UserDetailRepository {
    override suspend fun getUserProfileById(userId: Int): ProfileEntity? {
        return roomDao.getUserProfileById(userId)
    }
}