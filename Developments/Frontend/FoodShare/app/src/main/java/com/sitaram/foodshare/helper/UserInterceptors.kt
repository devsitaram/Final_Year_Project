package com.sitaram.foodshare.helper

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.google.gson.Gson
import com.sitaram.foodshare.features.login.data.pojo.Authentication
import okhttp3.Interceptor
import okhttp3.Response

class UserInterceptors(context: Context) : Interceptor {

    private val sharedPreferences =
        context.getSharedPreferences("food_donation_preferences", Context.MODE_PRIVATE)

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = getAuthenticate()
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${accessToken?.accessToken}")
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

    fun getPreInstEditor(): Editor {
        return getPreferenceInstance().edit()
    }

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

    fun installApp(): String {
        return sharedPreferences.getString("appInstallation", "") ?: ""
    }

    fun saveEmail(): String {
        return sharedPreferences.getString("email", "") ?: ""
    }

    fun getUserId(): Int {
        return getAuthenticate()?.id ?: 0
    }

    fun getUserEmail(): String {
        return getAuthenticate()?.email ?: ""
    }

    fun getUserName(): String {
        return getAuthenticate()?.username ?: ""
    }

    fun getUserRole(): String {
        return getAuthenticate()?.role ?: ""
    }
}