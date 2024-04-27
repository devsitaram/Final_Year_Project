package com.sitaram.foodshare.features.dashboard.profile.domain

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

// update user profile
@Keep
data class ProfileModelDAO(
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("address")
    var address: String? = null,
    @SerializedName("contact_number")
    var contactNum: String? = null,
    @SerializedName("gender")
    var gender: String? = null,
    @SerializedName("date_of_birth")
    var dob: String? = null,
    @SerializedName("abouts_user")
    var aboutsUser: String? = null
)