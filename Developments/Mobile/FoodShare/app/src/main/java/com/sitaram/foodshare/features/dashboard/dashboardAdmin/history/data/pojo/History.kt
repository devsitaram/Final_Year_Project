package com.sitaram.foodshare.features.dashboard.dashboardAdmin.history.data.pojo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data.Users

@Keep
data class History(
    @SerializedName("donor")
    var donor: Users? = Users(),
    @SerializedName("food")
    var food: Food? = Food(),
    @SerializedName("history")
    var foodHistory: FoodHistory? = FoodHistory(),
    @SerializedName("volunteer")
    var volunteer: Users? = Users()
)