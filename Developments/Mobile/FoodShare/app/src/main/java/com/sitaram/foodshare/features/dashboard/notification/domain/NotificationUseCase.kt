package com.sitaram.foodshare.features.dashboard.notification.domain

import android.util.Log
import com.sitaram.foodshare.features.dashboard.notification.data.NotificationPojo
import com.sitaram.foodshare.helper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NotificationUseCase(private val notificationRepository: NotificationRepository) {

    operator fun invoke(): Flow<Resource<NotificationPojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = notificationRepository.getNotifications()
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception){
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }
}