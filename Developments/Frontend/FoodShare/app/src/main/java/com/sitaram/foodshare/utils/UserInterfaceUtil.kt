package com.sitaram.foodshare.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import com.sitaram.foodshare.MainActivity
import com.sitaram.foodshare.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UserInterfaceUtil {

    companion object {

        // sow toast message
        fun showToast(context: Context?, message: String?) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        // get the app version
        fun getAppVersion(context: Context): String {
            try {
                val pInfo: PackageInfo =
                    context.packageManager.getPackageInfo(context.packageName, 0)
                return pInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return "N/A"
        }

        // navigate to call
        fun getPhoneCall(phoneNumber: String?, context: Context) {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            context.startActivity(intent)
        }

        fun getEmailSend(email: String?, context: Context) {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "message/rfc822"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            try {
                context.startActivity(Intent.createChooser(emailIntent, "Send Email"))
            } catch (ex: ActivityNotFoundException) {
                // Handle the case where there's no email client installed on the device
                Toast.makeText(context, "No email clients installed.", Toast.LENGTH_SHORT).show()
            }
        }

        fun showLocalNotification(context: Context, title: String, description: String) {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            val pendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            }

            // Create the notification
            val notification = NotificationCompat.Builder(context, "notification_channel")
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(description)
                .setColor(ContextCompat.getColor(context, R.color.primary))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true) // Dismiss the notification when clicked
                .build()

            // Show the notification
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, notification)
        }

        // open the google map
        fun openMapWithLocation(
            destinationLatitude: Double,
            destinationLongitude: Double,
            context: Context
        ) {
            val uri = "geo:?q=$destinationLatitude,$destinationLongitude"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            context.startActivity(intent)
        }

        // Get current data
        fun getCurrentDate(): String {
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return currentDate.format(formatter)
        }


        fun getRatingStars(rating: Int?): String {
            val yellowStars = rating ?: 0
            val grayStars = 5 - yellowStars

            val yellowStar = "\u2B50" // Yellow star emoji ⭐
            val grayStar = "\u2B50\uFE0C"

            return buildString {
                repeat(yellowStars) {
                    append("⭐") // Yellow star ⭐
                }
                repeat(grayStars) {
                    append("✰") // Gray star ✰
                }
            }
        }

        fun setRatingPoint(rating: Int?): String {
            val yellowStars = rating ?: 0
            val grayStars = 1 - yellowStars
            return buildString {
                repeat(yellowStars) {
                    append("⭐") // Yellow star ⭐
                }
                repeat(grayStars) {
                    append("✰") // Gray star ✰
                }
            }
        }
    }
}