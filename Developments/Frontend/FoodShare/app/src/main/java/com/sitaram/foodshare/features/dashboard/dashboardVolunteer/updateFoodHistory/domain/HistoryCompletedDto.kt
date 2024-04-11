package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.domain

import com.google.gson.annotations.SerializedName

data class HistoryCompletedDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("descriptions")
    val descriptions: String? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("rating")
    val rating: Int? = null,
)
