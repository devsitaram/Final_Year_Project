package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain

import android.content.Context
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.data.pojo.PendingPojo
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DistributedHistoryUseCase(private val distributedHistoryRepository: DistributedHistoryRepository) {

    operator fun invoke(userId: Int?, status: String?): Flow<Resource<PendingPojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = distributedHistoryRepository.getPendingFood(userId, status)
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception){
            emit(Resource.Error(message =  "Unable to connect to the server."))
        }
    }

    operator fun invoke(reportDTO: ReportDTO?): Flow<Resource<ResponsePojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = distributedHistoryRepository.getReportToUser(reportDTO)
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception){
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }

    operator fun invoke(historyId: Int?, username: String?, context: Context?): Flow<Resource<ResponsePojo?>> = flow {
        emit(Resource.Loading())
        try {
            val result = distributedHistoryRepository.getDeletedHistory(historyId, username)
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