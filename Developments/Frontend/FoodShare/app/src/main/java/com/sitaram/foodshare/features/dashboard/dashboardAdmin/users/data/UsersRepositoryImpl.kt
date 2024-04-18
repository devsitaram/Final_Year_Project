package com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data

import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.domain.RequestModelDAO
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.domain.UsersRepository

class UsersRepositoryImpl(private val apiService: ApiService) : UsersRepository {
    override suspend fun isAccountVerify(userId: Int?, isVerify: Boolean?): UsersPojo {
        return try {
            val result = apiService.userAccountVerify(RequestModelDAO(userId, isVerify))
            if (result?.isSuccess == true){
                result
            } else {
                UsersPojo(message = result?.message, isSuccess = result?.isSuccess)
            }
        } catch (e: Exception){
            UsersPojo(message = "Invalid user", isSuccess = false)
        }
    }

    override suspend fun getAllTypesOfUsers(): UsersPojo? {
        return apiService.getAllTypesOfUser()
    }

}