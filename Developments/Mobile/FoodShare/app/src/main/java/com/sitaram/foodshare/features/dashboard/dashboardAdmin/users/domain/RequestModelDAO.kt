package com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.domain

import com.google.gson.annotations.SerializedName

data class RequestModelDAO (
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("query")
    val updateQuery: Boolean? = null
)