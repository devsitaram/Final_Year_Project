package com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.data

import com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.domain.DonorHistoryRepository
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.ReportDTO
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

class DonorHistoryRepoImpl(private val apiService: ApiService):
    DonorHistoryRepository {

    override suspend fun getDonorHistory(id: Int?): DonorHistoryPojo? {
        return apiService.getDonorHistory(id)
    }

    override suspend fun getDeleteHistory(id: Int?, username: String?): ResponsePojo? {
        return apiService.getDeleteFood(id, username)
    }

    override suspend fun getReportToUser(reportDTO: ReportDTO): ResponsePojo? {
        return apiService.getReportToUser(reportDTO)
    }

}