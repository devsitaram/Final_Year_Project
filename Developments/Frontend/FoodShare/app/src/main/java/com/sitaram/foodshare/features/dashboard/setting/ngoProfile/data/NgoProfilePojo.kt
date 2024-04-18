package com.sitaram.foodshare.features.dashboard.setting.ngoProfile.data


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class NgoProfilePojo(
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("ngo")
    var ngo: NgoProfile? = null,
    @SerializedName("is_success")
    var isSuccess: Boolean? = null,
    @SerializedName("status")
    var status: Int? = null
)

@Keep
data class NgoProfile(
    @SerializedName("abouts_ngo")
    var aboutsNgo: String? = null,
    @SerializedName("created_by")
    var createdBy: String? = null,
    @SerializedName("created_date")
    var createdDate: String? = null,
    @SerializedName("established_date")
    var establishedDate: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("is_delete")
    var isDelete: Boolean? = null,
    @SerializedName("modify_by")
    var modifyBy: String? = null,
    @SerializedName("modify_date")
    var modifyDate: String? = null,
    @SerializedName("ngo_contact")
    var ngoContact: String? = null,
    @SerializedName("ngo_email")
    var ngoEmail: String? = null,
    @SerializedName("ngo_location")
    var ngoLocation: String? = null,
    @SerializedName("ngo_name")
    var ngoName: String? = null,
    @SerializedName("ngo_stream_url")
    var ngoStreamUrl: String? = null
)