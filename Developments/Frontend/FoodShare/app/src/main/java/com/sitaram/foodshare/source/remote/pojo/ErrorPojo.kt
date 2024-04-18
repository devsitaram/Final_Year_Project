package com.sitaram.foodshare.source.remote.pojo


// This is the group of error pojo or response
data class ErrorPojo (
    val code: Int? = null,
    val message: String? = null,
    val details: String? = null
)