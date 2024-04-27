package com.sitaram.foodshare.features.dashboard.notification.data

import com.sitaram.foodshare.features.dashboard.notification.domain.NotificationRepository
import com.sitaram.foodshare.source.remote.api.ApiService

class NotificationRepoImpl(private val apiService: ApiService): NotificationRepository {
    override suspend fun getNotifications(): NotificationPojo? {
        return apiService.getNotification()
    }
}