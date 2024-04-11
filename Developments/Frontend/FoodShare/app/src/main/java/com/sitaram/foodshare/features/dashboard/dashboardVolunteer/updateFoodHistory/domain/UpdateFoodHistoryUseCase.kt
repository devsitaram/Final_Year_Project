package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.domain

import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateFoodHistoryUseCase(private val updateFoodHistoryRepository: UpdateFoodHistoryRepository) {

    operator fun invoke(historyId: Int): Flow<Resource<HistoryEntity>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(updateFoodHistoryRepository.getHistoryDetails(historyId)))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Not found!"))
        }
    }

    operator fun invoke(historyCompletedDto: HistoryCompletedDto?): Flow<Resource<ResponsePojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = updateFoodHistoryRepository.getDonationCompleted(historyCompletedDto)
            if (result?.isSuccess == true) {
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }

        } catch (e: Exception) {
            emit(Resource.Error(message = "Not found"))
        }
    }

}