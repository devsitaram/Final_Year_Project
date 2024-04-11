package com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DonationModelDAO(
    @SerializedName("created_by")
    var createdBy: String? = null,
    @SerializedName("descriptions")
    var descriptions: String? = null,
    @SerializedName("donor")
    var donor: Int? = null,
    @SerializedName("food_name")
    var foodName: String? = null,
    @SerializedName("food_types")
    var foodTypes: String? = null,
    @SerializedName("latitude")
    var latitude: Double? = null,
    @SerializedName("longitude")
    var longitude: Double? = null,
    @SerializedName("pick_up_location")
    var pickUpLocation: String? = null,
    @SerializedName("quantity")
    var quantity: Int? = null,
    @SerializedName("expire_time")
    var expireTime: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("modify_by")
    var modifyBy: String? = null
)