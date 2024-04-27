package com.sitaram.foodshare.features.dashboard.setting.manageAccount.domain

import com.google.gson.annotations.SerializedName

/**
 * Manage account profile
 * update password
 * update both email and password
 */

// update password
data class UpdatePassword(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("new_password")
    val newPassword: String? = null
)

// delete account
data class DeleteAccount(
    @SerializedName("email")
    val email: String,
    @SerializedName("query")
    val updateQuery: Boolean? = null,
)