package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.presentation

import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.data.pojo.History
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

data class DistributedHistoryState(
    val isLoading: Boolean = false,
    val data: List<History?>? = null,
    val error: String? = null,
    val message: String? = null
)

data class ReportUserState(
    val isLoading: Boolean = false,
    val data: ResponsePojo? = null,
    val error: String? = null,
    val message: String? = null
)