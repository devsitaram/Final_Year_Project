package com.sitaram.foodshare.features.dashboard.history.domain

import com.sitaram.foodshare.features.dashboard.history.data.pojo.FoodHistoryPojo

interface HistoryRepository {
    suspend fun getFoodHistory(userId: Int?, status: String?): FoodHistoryPojo?
}