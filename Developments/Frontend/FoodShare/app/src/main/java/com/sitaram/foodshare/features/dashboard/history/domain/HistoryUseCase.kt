package com.sitaram.foodshare.features.dashboard.history.domain

import com.sitaram.foodshare.features.dashboard.history.data.pojo.FoodHistoryPojo
import com.sitaram.foodshare.helper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryUseCase(private val historyRepository: HistoryRepository) {
    operator fun invoke(userId: Int?, status: String?): Flow<Resource<FoodHistoryPojo?>> = flow {
        emit(Resource.Loading())
        try {
            val result = historyRepository.getFoodHistory(userId, status)
            if (result?.isSuccess == true) {
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "Not found!"))
        }
    }

}