package com.sitaram.foodshare.features.pushNotification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sitaram.foodshare.MainActivity
import com.sitaram.foodshare.R
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showNotification

const val CHANNEL_ID = "notification_channel"
const val CHANNEL_NAME = "com.sitaram.foodshare"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("FCM Token 1", token)
        // Save the token if needed
    }

    // New fcm access token generated
    fun getTokenInstance(): Task<String> {
        return FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result
            } else {
                task.exception
            }
        }
    }

    // show notification
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            generateNotification(remoteMessage.notification?.title ?: "", remoteMessage.notification?.body ?: "")
        }
    }

    // Generate notification
    private fun generateNotification(title: String, message: String){

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivities(this, 0, arrayOf(intent),
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        // channel id, channel name
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setLocalOnly(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())
    }

    // attach the notification created with the custom layout
    private fun getRemoteView(title: String, message: String): RemoteViews {
        Log.e("Notification","Title: $title, message: $message")
        val remoteView = RemoteViews("com.sitaram.foodshare", R.layout.notification)
        remoteView.setTextViewText(R.id.tvTitle, title)
        remoteView.setTextViewText(R.id.tvDescriptions, message)
        return remoteView
    }
}