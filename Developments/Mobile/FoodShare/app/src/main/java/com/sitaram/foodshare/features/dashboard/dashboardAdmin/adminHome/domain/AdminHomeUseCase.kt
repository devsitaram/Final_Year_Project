package com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.domain

import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.data.pojo.ReportPojo
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.domain.RequestModelDAO
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AdminHomeUseCase(private val adminHomeRepository: AdminHomeRepository) {

    operator fun invoke(): Flow<Resource<ReportPojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = adminHomeRepository.getReportDetails()
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception){
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }

    operator fun invoke(request: RequestModelDAO?): Flow<Resource<ResponsePojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = adminHomeRepository.verifyReport(request)
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (e: Exception){
            emit(Resource.Error(message = "Unable to connect to the server"))
        }
    }
}