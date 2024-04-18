package com.sitaram.foodshare.features.dashboard.home.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data.Users

data class FoodPojo(
	@SerializedName("message")
	var message: String? = null,
	@SerializedName("is_success")
	var isSuccess: Boolean? = null,
	@SerializedName("status")
	var status: Int? = null,
	@SerializedName("notification")
	var notification: Int? = null,
	@SerializedName("foods")
	var foods: List<Food?>? = null
)

@Keep
data class Food(
	@SerializedName("created_by")
	var createdBy: String? = null,
	@SerializedName("created_date")
	var createdDate: String? = null,
	@SerializedName("descriptions")
	var descriptions: String? = null,
	@SerializedName("food_types")
	var foodTypes: String? = null,
	@SerializedName("food_name")
	var foodName: String? = null,
	@SerializedName("id")
	var id: Int? = null,
	@SerializedName("is_delete")
	var isDelete: Boolean? = null,
	@SerializedName("modify_by")
	var modifyBy: String? = null,
	@SerializedName("modify_date")
	var modifyDate: String? = null,
	@SerializedName("pick_up_location")
	var pickUpLocation: String? = null,
	@SerializedName("expire_time")
	var expireTime: String? = null,
	@SerializedName("latitude")
	var latitude: Double? = null,
	@SerializedName("longitude")
	var longitude: Double? = null,
	@SerializedName("quantity")
	var quantity: Int? = null,
	@SerializedName("status")
	var status: String? = null,
	@SerializedName("stream_url")
	var streamUrl: String? = null,
	@SerializedName("donor")
	var user: Users? = null,
	@SerializedName("user_id")
	var userId: Int? = null,
	@SerializedName("ngo")
	val ngo: Int? = null
)
