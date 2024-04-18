package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.donationRating.data

import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.donationRating.domain.FoodDonateRatingDto
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.donationRating.domain.DonationRatingRepository
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.local.RoomDao
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

class DonationRatingRepoImpl(private val apiService: ApiService, private val roomDao: RoomDao): DonationRatingRepository {

    override suspend fun getHistoryDetails(historyId: Int): HistoryEntity? {
        return roomDao.getFoodHistoryById(historyId)
    }

    override suspend fun getDonationRating(foodDonateRatingDto: FoodDonateRatingDto?): ResponsePojo? {
        return apiService.donationRating(foodDonateRatingDto)
    }
}