package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain

import com.google.gson.annotations.SerializedName

data class ReportDTO(
    @SerializedName("complaint_to")
    val complaintTo: String? = null,
    @SerializedName("descriptions")
    val descriptions: String? = null,
    @SerializedName("created_by")
    val createdBy: String? = null,
    @SerializedName("food")
    val food: Int? = null,
)