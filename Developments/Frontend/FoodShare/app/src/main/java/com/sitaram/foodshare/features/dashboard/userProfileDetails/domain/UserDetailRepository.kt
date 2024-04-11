package com.sitaram.foodshare.features.dashboard.userProfileDetails.domain

import com.sitaram.foodshare.source.local.ProfileEntity

interface UserDetailRepository {
    suspend fun getUserProfileById(userId: Int): ProfileEntity?
}