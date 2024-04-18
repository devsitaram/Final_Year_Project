package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.donationRating.domain

import com.sitaram.foodshare.helper.Resource
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DonationRatingUseCase(private val donationRatingRepository: DonationRatingRepository) {

    operator fun invoke(historyId: Int): Flow<Resource<HistoryEntity>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(donationRatingRepository.getHistoryDetails(historyId)))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }

    operator fun invoke(foodDonateRatingDto: FoodDonateRatingDto?): Flow<Resource<ResponsePojo>> = flow {
        emit(Resource.Loading())
        try {
            val result = donationRatingRepository.getDonationRating(foodDonateRatingDto)
            if (result?.isSuccess == true) {
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Error(message = result?.message))
            }

        } catch (e: Exception) {
            emit(Resource.Error(message = "Unable to connect to the server."))
        }
    }

}