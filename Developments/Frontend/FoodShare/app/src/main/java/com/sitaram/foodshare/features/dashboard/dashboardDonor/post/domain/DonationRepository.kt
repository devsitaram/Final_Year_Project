package com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain

import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import java.io.File

interface DonationRepository {
    suspend fun foodDonate(file: File?, donationModelDAO: DonationModelDAO?): ResponsePojo?
}
