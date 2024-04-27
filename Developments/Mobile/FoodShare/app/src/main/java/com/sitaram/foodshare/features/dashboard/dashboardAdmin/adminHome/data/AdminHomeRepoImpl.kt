package com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.data

import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.data.pojo.ReportPojo
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.domain.AdminHomeRepository
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.domain.RequestModelDAO
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

class AdminHomeRepoImpl(private val apiService: ApiService): AdminHomeRepository {
    override suspend fun getReportDetails(): ReportPojo? {
        return apiService.getReportDetails()
    }

    override suspend fun verifyReport(request: RequestModelDAO?): ResponsePojo? {
        return apiService.setReportVerify(request)
    }

}