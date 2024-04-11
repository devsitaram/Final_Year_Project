package com.sitaram.foodshare.features.dashboard.dashboardDonor.post.data

import android.content.Context
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationModelDAO
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationRepository
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class DonationRepositoryImpl(private val apiService: ApiService): DonationRepository {

    override suspend fun foodDonate(file: File?, donationModelDAO: DonationModelDAO?): ResponsePojo? {
        try {
            val requestFile = file?.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("stream_url", file?.name ?: "No food", requestFile ?: error("File is null"))

            val dynamicParamsMap = mutableMapOf<String, RequestBody>()
            donationModelDAO?.let {
                dynamicParamsMap["created_by"] = it.createdBy?.toRequestBody() ?: "".toRequestBody()
                dynamicParamsMap["donor"] = it.donor?.toString()?.toRequestBody() ?: "".toRequestBody()
                dynamicParamsMap["food_types"] = it.foodTypes?.toRequestBody() ?: "".toRequestBody()
                dynamicParamsMap["latitude"] = it.latitude?.toString()?.toRequestBody() ?: "".toRequestBody()
                dynamicParamsMap["longitude"] = it.longitude?.toString()?.toRequestBody() ?: "".toRequestBody()
                dynamicParamsMap["pick_up_location"] = it.pickUpLocation?.toRequestBody() ?: "".toRequestBody()
                dynamicParamsMap["quantity"] = it.quantity?.toString()?.toRequestBody() ?: "".toRequestBody()
                dynamicParamsMap["expire_time"] = it.expireTime?.toRequestBody() ?: "".toRequestBody()
                dynamicParamsMap["status"] = it.status?.toRequestBody() ?: "".toRequestBody()
                dynamicParamsMap["food_name"] = it.foodName?.toRequestBody() ?: "".toRequestBody()
                dynamicParamsMap["descriptions"] = it.descriptions?.toRequestBody() ?: "".toRequestBody()
            }

            return apiService.newFoodDonation(dynamicParamsMap, imagePart)

        } catch (e: Exception) {
            // Handle exceptions here
            return ResponsePojo(isSuccess = false, message = e.message)
        }
    }

}