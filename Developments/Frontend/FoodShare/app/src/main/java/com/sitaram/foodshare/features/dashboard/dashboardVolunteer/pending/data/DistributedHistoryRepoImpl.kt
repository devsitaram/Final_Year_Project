package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.data

import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.data.pojo.PendingPojo
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.DistributedHistoryRepository
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.ReportDTO
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.local.RoomDao
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

class DistributedHistoryRepoImpl(private val apiService: ApiService): DistributedHistoryRepository {
    override suspend fun getPendingFood(userId: Int?, status: String?): PendingPojo? {
        return apiService.getPendingFood(userId, status)
    }

    override suspend fun getReportToUser(reportDTO: ReportDTO?): ResponsePojo? {
        return apiService.getReportToUser(reportDTO)
    }

    override suspend fun getDeletedHistory(historyId: Int?, username: String?): ResponsePojo? {
        return apiService.getDeleteHistory(historyId, username)
    }

//    override suspend fun setHistoryFoodDetail(history: HistoryEntity) {
//        roomDao.insertFoodHistory(history)
//    }

}