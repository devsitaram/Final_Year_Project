package com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.data.pojo


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep

data class ReportDetails(
    @SerializedName("complaint_to")
    var complaintTo: String? = null,
    @SerializedName("created_by")
    var createdBy: String? = null,
    @SerializedName("created_date")
    var createdDate: String? = null,
    @SerializedName("descriptions")
    var descriptions: String? = null,
    @SerializedName("food")
    var food: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("is_delete")
    var isDelete: Boolean? = null,
    @SerializedName("is_verify")
    var isVerify: Boolean? = null,
    @SerializedName("modify_by")
    var modifyBy: Any? = null,
    @SerializedName("modify_date")
    var modifyDate: Any? = null
)