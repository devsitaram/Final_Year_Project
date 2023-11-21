package com.donation.fda.data.common

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class ClientInterceptors(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val sharedPreferences = context.getSharedPreferences("social_media_preferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("accessToken", "")

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}