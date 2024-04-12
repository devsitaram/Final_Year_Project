package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain

import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.data.pojo.PendingPojo
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

interface DistributedHistoryRepository {

    suspend fun getPendingFood(userId: Int?, status: String?): PendingPojo?
    suspend fun getReportToUser(reportDTO: ReportDTO?): ResponsePojo?
    suspend fun getDeletedHistory(historyId: Int?, username: String?): ResponsePojo?
}