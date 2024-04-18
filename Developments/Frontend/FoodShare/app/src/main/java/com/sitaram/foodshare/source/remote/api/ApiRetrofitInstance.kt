package com.sitaram.foodshare.source.remote.api

import android.content.Context
import com.sitaram.foodshare.utils.ApiUrl
import com.sitaram.foodshare.helper.UserInterceptors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Singleton class responsible for providing a Retrofit instance configured for API calls.
 */
class ApiRetrofitInstance {
    companion object {
        private var retrofitInstance: Retrofit? = null

        /**
         * Get the Retrofit instance.
         * @param context The context required for creating OkHttpClient.
         * @return Retrofit instance.
         */
        fun getRetrofitInstance(context: Context): Retrofit {
            if (retrofitInstance == null) {
                // Create the object of HttpLoggingInterceptor
                val httpLoggingInterceptor =
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                // Create object of OkHttpClient
                val okHttpClient = OkHttpClient.Builder()
                        .addInterceptor(httpLoggingInterceptor)
                        .addInterceptor(UserInterceptors(context)) // Add the custom interceptor
                        .connectTimeout(30, TimeUnit.SECONDS) // Set connection timeout
                        .readTimeout(30, TimeUnit.SECONDS) // Set read timeout
                        .writeTimeout(30, TimeUnit.SECONDS) // Set write timeout
                        .build()
                // Return the Retrofit instance
                retrofitInstance = Retrofit.Builder()
                    .baseUrl(ApiUrl.API_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()) // json converter
                    .build()
            }
            return retrofitInstance!!
        }
    }
}