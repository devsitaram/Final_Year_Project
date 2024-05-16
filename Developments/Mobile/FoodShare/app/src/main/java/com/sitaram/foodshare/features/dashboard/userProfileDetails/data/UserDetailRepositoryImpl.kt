package com.sitaram.foodshare.features.dashboard.userProfileDetails.data

import com.sitaram.foodshare.source.local.ProfileEntity
import com.sitaram.foodshare.source.local.RoomDao
import com.sitaram.foodshare.features.dashboard.userProfileDetails.domain.UserDetailRepository
import com.sitaram.foodshare.source.remote.api.ApiService

class UserDetailRepositoryImpl(private val roomDao: RoomDao, private val apiService: ApiService) :
    UserDetailRepository {
    override suspend fun getUserProfileById(userId: Int): ProfileEntity? {
        val userProfile = roomDao.getUserProfileById(userId)
        if (userProfile == null) {
            val result = apiService.getUserById(userId)
            if (result?.isSuccess == true) {
                return result.userProfile?.let {
                    ProfileEntity(
                        id = it.id,
                        role = it.role,
                        address = it.address,
                        isActive = it.isActive,
                        gender = it.gender,
                        lastLogin = it.lastLogin,
                        dateOfBirth = it.dateOfBirth,
                        modifyBy = it.modifyBy,
                        createdBy = it.createdBy,
                        contactNumber = it.contactNumber,
                        isDelete = it.isDelete,
                        isAdmin = it.isAdmin,
                        aboutsUser = it.aboutsUser,
                        photoUrl = it.photoUrl,
                        createdDate = it.createdDate,
                        modifyDate = it.modifyDate,
                        email = it.email,
                        username = it.username,
                        ngo = it.ngo
                    )
                }
            } else {
                return roomDao.getUserProfileById(userId)
            }
        } else {
            return userProfile
        }
    }
}