package com.sitaram.foodshare.source.remote.pojo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

// This is the success but not return the data pojo
@Keep
data class ResponsePojo(
    @SerializedName("is_success")
    var isSuccess: Boolean? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    val status: Int? = null
)