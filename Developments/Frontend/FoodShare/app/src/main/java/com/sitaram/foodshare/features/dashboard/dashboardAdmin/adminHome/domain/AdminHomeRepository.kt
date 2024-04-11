package com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.domain

import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.data.pojo.ReportPojo
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

interface AdminHomeRepository {
    suspend fun getReportDetails(): ReportPojo?
    suspend fun verifyReport(id: Int?, isVerify: Boolean?): ResponsePojo?
}