package com.sitaram.foodshare.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Entity of local database
 * tables are: Food, Profile, History
 */
@Entity(tableName = "profile")
class ProfileEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int? = null,
    @ColumnInfo("role")
    val role: String? = null,
    @ColumnInfo("address")
    val address: String? = null,
    @ColumnInfo("is_active")
    val isActive: Boolean? = null,
    @ColumnInfo("gender")
    val gender: String? = null,
    @ColumnInfo("last_login")
    val lastLogin: String? = null,
    @ColumnInfo("date_of_birth")
    val dateOfBirth: String? = null,
    @ColumnInfo("modify_by")
    val modifyBy: String? = null,
    @ColumnInfo("created_by")
    val createdBy: String? = null,
    @ColumnInfo("contact_number")
    val contactNumber: String? = null,
    @ColumnInfo("isDelete")
    val isDelete: Boolean? = null,
    @ColumnInfo("is_admin")
    val isAdmin: Boolean? = null,
    @ColumnInfo("abouts_user")
    val aboutsUser: String? = null,
    @ColumnInfo("photo_url")
    val photoUrl: String? = null,
    @ColumnInfo("created_date")
    val createdDate: String? = null,
    @ColumnInfo("modify_date")
    val modifyDate: String? = null,
    @ColumnInfo("email")
    val email: String? = null,
    @ColumnInfo("username")
    val username: String? = null,
    @SerializedName("ngo")
    val ngo: Int? = null
)

// Food table
@Entity(tableName = "foods")
class FoodsEntity(
    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null,
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
    @SerializedName("user_id")
    var userId: Int? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("contact_number")
    var contactNumber: String? = null,
    @ColumnInfo("photo_url")
    val photoUrl: String? = null,
)

// History table
@Entity(tableName = "history")
class HistoryEntity(
    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("created_by")
    var createdBy: String? = null,
    @SerializedName("created_date")
    var createdDate: String? = null,
    @SerializedName("descriptions")
    var descriptions: String? = null,
    @SerializedName("distributed_date")
    var distributedDate: String? = null,
    @SerializedName("distributed_location")
    var distributedLocation: String? = null,
    @SerializedName("status")
    var status: String? =null,
    @SerializedName("food")
    var food: Int? = null,
    @SerializedName("is_delete")
    var isDelete: Boolean? = null,
    @SerializedName("modify_by")
    var modifyBy: String? = null,
    @SerializedName("modify_date")
    var modifyDate: String? = null,
    @SerializedName("rating_point")
    var ratingPoint: Int? = null,
    @SerializedName("volunteer")
    var volunteer: Int? = null
)