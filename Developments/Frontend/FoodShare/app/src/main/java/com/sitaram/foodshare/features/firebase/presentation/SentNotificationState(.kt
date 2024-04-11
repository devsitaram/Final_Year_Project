package com.sitaram.foodshare.features.firebase.presentation

data class SentNotificationState(
    val isEnteringToken: Boolean = true,
    val remoteToken: String = "",
    val messageText: String = ""
)
