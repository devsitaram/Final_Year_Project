package com.donation.fda.data.common

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class ClientInterceptors(private val context: Context) : Interceptor {

    private val sharedPreferences = context.getSharedPreferences("food_donation_preferences", Context.MODE_PRIVATE)

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = sharedPreferences.getString("accessToken", "")

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }

    fun installApp(): String? {
        return sharedPreferences.getString("installToken", "")
    }

    fun userTypes(): String? {
        return sharedPreferences.getString("userTypes", "")
    }
}