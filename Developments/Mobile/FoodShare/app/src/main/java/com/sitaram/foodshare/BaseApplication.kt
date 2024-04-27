package com.sitaram.foodshare

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.sitaram.foodshare.utils.ApiUrl.Companion.CHANNEL_ID
import com.sitaram.foodshare.utils.ApiUrl.Companion.CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp

/**
 * The base application class of the application, annotated with @HiltAndroidApp for Hilt support.
 * In @onCreate class have give the local notification permission
 */
@HiltAndroidApp
class BaseApplication: Application(){
    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate() {
        super.onCreate()
        // Check the SDK version and create the notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}