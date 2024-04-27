package com.sitaram.foodshare.features.dashboard.notification.presentation

import com.sitaram.foodshare.features.dashboard.notification.data.NotificationPojo
import com.sitaram.foodshare.source.local.NotificationEntity

data class NotificationState(
    val isLoading: Boolean = false,
    val data: NotificationPojo? = null,
    val error: String? = null
)

data class NotificationLocalState(
    val isLoading: Boolean = false,
    val data: List<NotificationEntity>? = null,
    val error: String? = null
)
