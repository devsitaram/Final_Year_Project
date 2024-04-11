package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.data

import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.domain.HistoryCompletedDto
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.domain.UpdateFoodHistoryRepository
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.local.RoomDao
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

class UpdateFoodHistoryRepoImpl(private val apiService: ApiService, private val roomDao: RoomDao): UpdateFoodHistoryRepository {

    override suspend fun getHistoryDetails(historyId: Int): HistoryEntity? {
        return roomDao.getFoodHistoryById(historyId)
    }

    override suspend fun getDonationCompleted(historyCompletedDto: HistoryCompletedDto?): ResponsePojo? {
        return apiService.completedDonation(historyCompletedDto)
    }
}