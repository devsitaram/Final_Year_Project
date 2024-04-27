package com.sitaram.foodshare.features.dashboard.foodDetail.data

import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationModelDAO
import com.sitaram.foodshare.features.dashboard.foodDetail.data.pojo.FoodUpdatePojo
import com.sitaram.foodshare.features.dashboard.foodDetail.domain.FoodDetailRepository
import com.sitaram.foodshare.features.dashboard.foodDetail.domain.FoodModelDAO
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class FoodDetailRepositoryImpl(private val apiService: ApiService) : FoodDetailRepository {

    override suspend fun getAcceptFood(foodModelDAO: FoodModelDAO?): ResponsePojo? {
        return apiService.acceptFood(foodModelDAO)
    }
    override suspend fun getUpdateFoodDetails(
        foodId: Int?,
        donationModelDAO: DonationModelDAO?,
    ): FoodUpdatePojo? {
        return apiService.getUpdateDonateFoodDetails(foodId, donationModelDAO)
    }
    override suspend fun updateFoodImage(foodId: Int?, fileImage: File?): FoodUpdatePojo? {
        val requestFile = fileImage?.asRequestBody("image/*".toMediaTypeOrNull())
        val image = fileImage.let { MultipartBody.Part.createFormData("image", fileImage?.name ?: "food",  requestFile ?: error("File is null")) }
        val userIdRequestBody = foodId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        return apiService.getUpdateDonateFoodImage(userIdRequestBody, image)
    }
}