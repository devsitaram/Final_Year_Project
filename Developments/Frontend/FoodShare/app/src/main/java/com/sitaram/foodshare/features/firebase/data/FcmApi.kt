package com.sitaram.foodshare.features.firebase.data

import com.sitaram.foodshare.features.firebase.domain.NotificationBody
import com.sitaram.foodshare.features.firebase.domain.SentNotificationDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {

    @POST("/send")
    suspend fun sendMessage(
        @Body body: SentNotificationDTO
    )

    @POST("/broadcast")
    suspend fun broadcast(
        @Body body: SentNotificationDTO
    )
}