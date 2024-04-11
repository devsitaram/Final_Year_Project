package com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.domain

import com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.data.DonorHistoryPojo
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.ReportDTO
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

interface DonorHistoryRepository {

    suspend fun getDonorHistory(id: Int?): DonorHistoryPojo?
    suspend fun getDeleteHistory(id: Int?, username: String?): ResponsePojo?
    suspend fun getReportToUser(reportDTO: ReportDTO): ResponsePojo?
}