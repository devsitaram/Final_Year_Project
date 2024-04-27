package com.sitaram.foodshare.features.dashboard.notification.domain

import com.sitaram.foodshare.features.dashboard.notification.data.NotificationPojo

interface NotificationRepository {
    suspend fun getNotifications(): NotificationPojo?
}