package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.data.pojo


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data.Users

@Keep
data class History(
    @SerializedName("food_details")
    var foodDetails: FoodDetails? = null,
    @SerializedName("history_details")
    var historyDetails: HistoryDetails? = null,
    @SerializedName("user_details")
    var userDetails: Users? = null
)