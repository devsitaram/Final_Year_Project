package com.sitaram.foodshare.features.dashboard.foodDetail.domain

import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationModelDAO
import com.sitaram.foodshare.features.dashboard.foodDetail.data.pojo.FoodUpdatePojo
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import java.io.File

interface FoodDetailRepository {

    suspend fun getAcceptFood(foodModelDAO: FoodModelDAO?): ResponsePojo?
    suspend fun getUpdateFoodDetails(foodId: Int?, donationModelDAO: DonationModelDAO?): FoodUpdatePojo?
    suspend fun updateFoodImage(foodId: Int?, fileImage: File?): FoodUpdatePojo?
}