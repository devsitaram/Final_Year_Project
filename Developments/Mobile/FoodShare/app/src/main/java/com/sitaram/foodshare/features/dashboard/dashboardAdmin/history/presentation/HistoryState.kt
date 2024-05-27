package com.sitaram.foodshare.features.dashboard.dashboardAdmin.history.presentation

import com.sitaram.foodshare.features.dashboard.dashboardAdmin.history.data.pojo.FoodHistoryPojo

class HistoryState (
    val isLoading: Boolean = false,
    val data: FoodHistoryPojo? = null,
    val error: String? = null
)