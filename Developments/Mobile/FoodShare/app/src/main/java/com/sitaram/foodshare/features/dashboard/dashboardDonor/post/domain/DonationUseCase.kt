package com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain

import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class DonationUseCase(private val donationRepository: DonationRepository) {

    operator fun invoke(file: File?, donationModelDAO: DonationModelDAO?): Flow<Resource<ResponsePojo?>> = flow {
        emit(Resource.Loading())
        try {
            val response = donationRepository.foodDonate(file, donationModelDAO)
            if (response?.isSuccess == true){
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error(response?.message))
            }
        } catch (e: Exception){
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }
}
