package com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.data.pojo


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data.Users
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.data.pojo.FoodDetails

@Keep
data class Data(
    @SerializedName("food")
    var food: FoodDetails? = null,
    @SerializedName("report")
    var reportDetails: ReportDetails? = null,
    @SerializedName("user")
    var user: Users? = null
)