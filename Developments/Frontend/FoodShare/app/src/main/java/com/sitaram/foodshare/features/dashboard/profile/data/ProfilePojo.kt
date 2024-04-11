package com.sitaram.foodshare.features.dashboard.profile.data

import com.google.gson.annotations.SerializedName

data class ProfilePojo(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("is_success")
    val isSuccess: Boolean? = null,
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("user_profile")
    val userProfile: Profile? = null
)

data class Profile(
    @SerializedName("abouts_user")
    var aboutsUser: String? = null,
    @SerializedName("address")
    var address: String? = null,
    @SerializedName("contact_number")
    var contactNumber: String? = null,
    @SerializedName("created_by")
    var createdBy: String? = null,
    @SerializedName("created_date")
    var createdDate: String? = null,
    @SerializedName("date_of_birth")
    var dateOfBirth: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("gender")
    var gender: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("is_active")
    var isActive: Boolean? = null,
    @SerializedName("is_admin")
    var isAdmin: Boolean? = null,
    @SerializedName("is_delete")
    var isDelete: Boolean? = null,
    @SerializedName("last_login")
    var lastLogin: String? = null,
    @SerializedName("modify_by")
    var modifyBy: String? = null,
    @SerializedName("modify_date")
    var modifyDate: String? = null,
    @SerializedName("photo_url")
    var photoUrl: String? = null,
    @SerializedName("role")
    var role: String? = null,
    @SerializedName("username")
    var username: String? = null
)