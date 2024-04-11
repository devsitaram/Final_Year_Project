package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.data.pojo


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class HistoryDetails(
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
    @SerializedName("food")
    var food: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("is_delete")
    var isDelete: Boolean? = null,
    @SerializedName("modify_by")
    var modifyBy: String? = null,
    @SerializedName("modify_date")
    var modifyDate: String? = null,
    @SerializedName("rating_point")
    var ratingPoint: Int? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("stream_url")
    var streamUrl: String? = null,
    @SerializedName("volunteer")
    var volunteer: Int? = null
)