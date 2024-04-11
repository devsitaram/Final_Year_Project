package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.domain

import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

interface UpdateFoodHistoryRepository {

    suspend fun getHistoryDetails(historyId: Int): HistoryEntity?
    suspend fun getDonationCompleted(historyCompletedDto: HistoryCompletedDto?): ResponsePojo?
}
