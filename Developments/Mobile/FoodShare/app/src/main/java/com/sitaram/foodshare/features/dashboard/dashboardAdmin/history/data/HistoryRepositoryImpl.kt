package com.sitaram.foodshare.features.dashboard.dashboardAdmin.history.data

import com.sitaram.foodshare.features.dashboard.dashboardAdmin.history.data.pojo.FoodHistoryPojo
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.history.domain.HistoryRepository

class HistoryRepositoryImpl(private val apiService: ApiService): HistoryRepository {
    override suspend fun getFoodHistory(userId: Int?, status: String?): FoodHistoryPojo? {
        return apiService.getFoodHistory()
    }
}