package com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.data


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data.Users
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.history.data.pojo.FoodHistory

@Keep
data class DonorHistoryPojo(
    @SerializedName("data")
    var data: List<HistoryData?>? = null,
    @SerializedName("is_success")
    var isSuccess: Boolean? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Int? = null
)

@Keep
data class HistoryData(
    @SerializedName("foods")
    var foods: Foods? = null,
    @SerializedName("histories")
    var histories: FoodHistory? = null,
    @SerializedName("volunteer")
    var volunteer: Users? = null,
    @SerializedName("donor")
    var donor: Users? = null
)

@Keep
data class Foods(
    @SerializedName("created_by")
    var createdBy: String? = null,
    @SerializedName("created_date")
    var createdDate: String? = null,
    @SerializedName("descriptions")
    var descriptions: String? = null,
    @SerializedName("donor")
    var donor: Int? = null,
    @SerializedName("expire_time")
    var expireTime: String? = null,
    @SerializedName("food_name")
    var foodName: String? = null,
    @SerializedName("food_types")
    var foodTypes: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("is_delete")
    var isDelete: Boolean? = null,
    @SerializedName("latitude")
    var latitude: String? = null,
    @SerializedName("longitude")
    var longitude: String? = null,
    @SerializedName("modify_by")
    var modifyBy: String? = null,
    @SerializedName("modify_date")
    var modifyDate: String? = null,
    @SerializedName("pick_up_location")
    var pickUpLocation: String? = null,
    @SerializedName("quantity")
    var quantity: Int? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("stream_url")
    var streamUrl: String? = null
)