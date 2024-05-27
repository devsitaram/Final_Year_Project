package com.sitaram.foodshare.features.dashboard.dashboardAdmin.history.domain

import com.sitaram.foodshare.features.dashboard.dashboardAdmin.history.data.pojo.FoodHistoryPojo

interface HistoryRepository {
    suspend fun getFoodHistory(userId: Int?, status: String?): FoodHistoryPojo?
}