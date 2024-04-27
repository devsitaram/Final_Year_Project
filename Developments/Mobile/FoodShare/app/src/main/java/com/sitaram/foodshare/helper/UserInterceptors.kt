package com.sitaram.foodshare.helper

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.google.gson.Gson
import com.sitaram.foodshare.features.dashboard.pushNotification.FcmDeviceToken
import com.sitaram.foodshare.features.login.data.pojo.Authentication
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor for handling user authentication headers.
 * @param context The context.
 * This class have sharedPreferences instance create to save the user data
 */
class UserInterceptors(context: Context) : Interceptor {

    // Shared preferences instance
    private val sharedPreferences = context.getSharedPreferences("food_donation_preferences", Context.MODE_PRIVATE)

    /**
     * Intercepts the network requests and adds the authorization header.
     * @param chain The interceptor chain.
     * @return The response from the server.
     * There add the headers
     * Authorization: Bearer + token (Bear or authentication token)
     * Content-Type: application/json (pre inform to https verb for json data like: GET, POST, PATCH etc)
     * Content-Disposition: form-data (like: text, file etc.)
     * Content-Transfer-Encoding: binary (the binary converter multipart data)
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = getAccessToken()
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .addHeader("Content-Type", "application/json")
            .addHeader("Content-Disposition", "form-data")
            .addHeader("Content-Transfer-Encoding", "binary")
            .build()

        return chain.proceed(request)
    }


    // return the share shared preference instance
    fun getPreferenceInstance(): SharedPreferences {
        return sharedPreferences
    }

    // Shared preferences editor object
    fun getPreInstEditor(): Editor {
        return getPreferenceInstance().edit()
    }

    // Get user authentication details
    fun getAuthenticate(): Authentication? {
        val jsonString = sharedPreferences.getString("authentication", "")
        if (!jsonString.isNullOrEmpty()) {
            return try {
                Gson().fromJson(jsonString, Authentication::class.java)
            } catch (e: Exception) {
                null
            }
        }
        return null
    }

    // Get access token
    fun getAccessToken(): String {
        return getAuthenticate()?.accessToken ?: ""
    }

    // Get app install instance
    fun installApp(): String {
        return sharedPreferences.getString("appInstallation", "") ?: ""
    }

    // Save FCM Token
    fun getFcmDeviceToke(): String {
        return sharedPreferences.getString("fcmDeviceToken", "") ?: ""
    }

    // Save FCM Token
    fun getSystemToke(): String {
        return sharedPreferences.getString("systemToken", "") ?: ""
    }

    // Save new email
    fun getRememberEmail(): String {
        return sharedPreferences.getString("email", "") ?: ""
    }

    // Get user id
    fun getUserId(): Int {
        return getAuthenticate()?.id ?: 0
    }

    // get user email
    fun getUserEmail(): String {
        return getAuthenticate()?.email ?: ""
    }

    // get username
    fun getUserName(): String {
        return getAuthenticate()?.username ?: ""
    }

    // get user role
    fun getUserRole(): String {
        return getAuthenticate()?.role ?: ""
    }
}