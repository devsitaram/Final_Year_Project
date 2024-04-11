package com.sitaram.foodshare.features.firebase.domain

data class SentNotificationDTO(
    val to: String?,
    val notification: NotificationBody
)

data class NotificationBody(
    val title: String,
    val body: String
)
