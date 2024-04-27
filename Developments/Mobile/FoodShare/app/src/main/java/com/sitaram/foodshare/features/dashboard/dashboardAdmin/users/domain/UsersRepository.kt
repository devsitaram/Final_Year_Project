package com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.domain

import com.sitaram.foodshare.source.local.ProfileEntity
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data.UsersPojo

interface UsersRepository {
    suspend fun isAccountVerify(userId: Int?, isVerify: Boolean?): UsersPojo?
    suspend fun getAllTypesOfUsers(): UsersPojo?

}