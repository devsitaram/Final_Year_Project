package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.donationRating.domain

import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo

interface DonationRatingRepository {

    suspend fun getHistoryDetails(historyId: Int): HistoryEntity?
    suspend fun getDonationRating(foodDonateRatingDto: FoodDonateRatingDto?): ResponsePojo?
}
