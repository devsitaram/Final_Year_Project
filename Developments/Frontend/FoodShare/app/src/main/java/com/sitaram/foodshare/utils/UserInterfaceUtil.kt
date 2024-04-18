package com.sitaram.foodshare.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.sitaram.foodshare.MainActivity
import com.sitaram.foodshare.R
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.source.local.DatabaseHelper
import com.sitaram.foodshare.utils.ApiUrl.Companion.CHANNEL_ID
import com.sitaram.foodshare.utils.ApiUrl.Companion.TOKEN_SECRET_KEY
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

/**
 * Utility class for common user interface-related operations.
 */
class UserInterfaceUtil {

    companion object {

        // Show toast message
        fun showToast(context: Context?, message: String?) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        // Get the app version
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

        // Initiates a phone call.
        fun getPhoneCall(phoneNumber: String?, context: Context) {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            context.startActivity(intent)
        }

        // Initiates sending an email.
        fun getEmailSend(email: String?, context: Context) {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "message/rfc822"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            try {
                context.startActivity(
                    Intent.createChooser(emailIntent, context.getString(R.string.send_email))
                )
            } catch (ex: ActivityNotFoundException) {
                showToast(context, context.getString(R.string.no_email_clients_installed))
            }
        }

        /**
         * Shows a notification.
         * @param context The application context.
         * @param title The title of the notification.
         * @param description The description of the notification.
         */
        fun showLocalNotification(context: Context, title: String, description: String) {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            val pendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            }

            // Create the notification
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_foreground) // ic_launcher_foreground
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

        /**
         * This function can check the toke is expire or not
         * If token is expire the return true otherwise false
         */
        fun isTokenExpired(token: String): Boolean {
            return try {
                val algorithm = Algorithm.HMAC256(TOKEN_SECRET_KEY)
                val verifier: JWTVerifier = JWT.require(algorithm).build()
                val decodedJWT: DecodedJWT = verifier.verify(token)
                val expirationTime: Date = decodedJWT.expiresAt
                val currentTime = Date()
                expirationTime.before(currentTime)
            } catch (exception: JWTVerificationException) {
                true
            }
        }

        // Check the location permission is one or not
        fun isLocationEnabled(context: Context): Boolean {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        // Clear the share preference and local database
        fun clearLocalStorage(context: Context): Boolean {
            return try {
                val getSharedPreferences = UserInterceptors(context)
                val getPreferenceInstance = getSharedPreferences.getPreferenceInstance()
                val editor = getPreferenceInstance.edit()
                editor?.putString("authentication", "")?.apply()
//                editor.putString("fcmDeviceToken", "").apply()
                DatabaseHelper.clearDatabase(context)
                true
            } catch (e: Exception){
                false
            }
        }

        // Get current data
        fun getCurrentDate(): String {
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return currentDate.format(formatter)
        }
    }
}