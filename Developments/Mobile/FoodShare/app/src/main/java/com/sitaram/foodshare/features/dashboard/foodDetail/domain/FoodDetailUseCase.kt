package com.sitaram.foodshare.features.dashboard.foodDetail.domain

import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationModelDAO
import com.sitaram.foodshare.features.dashboard.foodDetail.data.pojo.FoodUpdatePojo
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class FoodDetailUseCase(private val foodDetailRepository: FoodDetailRepository) {

    // Edit food
    operator fun invoke(foodId: Int?, donationModelDAO: DonationModelDAO?): Flow<Resource<FoodUpdatePojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = foodDetailRepository.getUpdateFoodDetails(foodId, donationModelDAO)
            if (result?.isSuccess == true) {
                    emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (ex: Exception) {
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }

    operator fun invoke(foodId: Int?, fileImage: File?): Flow<Resource<FoodUpdatePojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = foodDetailRepository.updateFoodImage(foodId, fileImage)
            if (result?.isSuccess == true) {
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (ex: Exception) {
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }

    // Accept the donate food
    operator fun invoke(foodModelDAO: FoodModelDAO): Flow<Resource<ResponsePojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = foodDetailRepository.getAcceptFood(foodModelDAO)
            if (result?.isSuccess == true){
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (ex: Exception){
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }
}