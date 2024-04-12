package com.sitaram.foodshare.features.dashboard.foodDetail.domain

import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationModelDAO
import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class FoodDetailUseCase(private val foodDetailRepository: FoodDetailRepository) {

    // Edit food
    operator fun invoke(foodId: Int?, donationModelDAO: DonationModelDAO?): Flow<Resource<FoodsEntity>> = flow {
        emit(Resource.Loading())
        try {
            val result = foodDetailRepository.getEditDonateFood(foodId, donationModelDAO)
            if (result?.isSuccess == true) {
                val food = result.food // Assuming this is the result of the editing/donation operation

                // Create a FoodsEntity object using the data from the edited/donated food
                val foodsEntity = FoodsEntity(
                    id = food?.id,
                    createdBy = food?.createdBy,
                    createdDate = food?.createdDate,
                    descriptions = food?.descriptions,
                    foodTypes = food?.foodTypes,
                    foodName = food?.foodName,
                    isDelete = food?.isDelete,
                    modifyBy = food?.modifyBy,
                    modifyDate = food?.modifyDate,
                    pickUpLocation = food?.pickUpLocation,
                    expireTime = food?.expireTime,
                    latitude = food?.latitude?.toDouble(),
                    longitude = food?.longitude?.toDouble(),
                    quantity = food?.quantity,
                    status = food?.status,
                    streamUrl = food?.streamUrl,
                    userId = food?.user?.id,
                )

                emit(Resource.Success(data = foodsEntity))
            } else {
                emit(Resource.Error(message = result?.message))
            }

        } catch (ex: Exception) {
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }

    operator fun invoke(foodId: Int?, fileImage: File?): Flow<Resource<FoodsEntity>> = flow {
        emit(Resource.Loading())
        try {
            val result = foodDetailRepository.updateFoodImage(foodId, fileImage)
            if (result?.isSuccess == true) {
                val food = result.food // Assuming this is the result of the editing/donation operation
                val foodsEntity = FoodsEntity(
                    id = food?.id,
                    createdBy = food?.createdBy,
                    createdDate = food?.createdDate,
                    descriptions = food?.descriptions,
                    foodTypes = food?.foodTypes,
                    foodName = food?.foodName,
                    isDelete = food?.isDelete,
                    modifyBy = food?.modifyBy,
                    modifyDate = food?.modifyDate,
                    pickUpLocation = food?.pickUpLocation,
                    expireTime = food?.expireTime,
                    latitude = food?.latitude,
                    longitude = food?.longitude,
                    quantity = food?.quantity,
                    status = food?.status,
                    streamUrl = food?.streamUrl,
                    userId = food?.user?.id,
                )

                emit(Resource.Success(data = foodsEntity))
            } else {
                emit(Resource.Error(message = result?.message))
            }
        } catch (ex: Exception) {
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }

    // accept the donate food
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