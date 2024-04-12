package com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.domain

import com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.data.DonorHistoryPojo
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.ReportDTO
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DonorHistoryUseCase(private val donorHistoryRepository: DonorHistoryRepository) {

    operator fun invoke(id: Int?): Flow<Resource<DonorHistoryPojo?>> = flow {
        emit(Resource.Loading())
        try {
            val result = donorHistoryRepository.getDonorHistory(id)
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (ex: Exception){
            emit(Resource.Error(message =  "Unable to connect to the server."))
        }
    }
    operator fun invoke(id: Int?, username: String?): Flow<Resource<ResponsePojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = donorHistoryRepository.getDeleteHistory(id, username)
            if(result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception){
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }

    operator fun invoke(reportDTO: ReportDTO): Flow<Resource<ResponsePojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = donorHistoryRepository.getReportToUser(reportDTO)
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