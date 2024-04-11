package com.sitaram.foodshare.features.dashboard.profile.domain

import com.sitaram.foodshare.features.dashboard.profile.data.ProfilePojo
import java.io.File


interface ProfileRepository {
    suspend fun getUserProfile(): ProfilePojo?
    suspend fun updateProfile(userId: Int?, updateProfile: ProfileModelDAO?): ProfilePojo?
    suspend fun updateProfilePicture(userId: Int?, imageFile: File?): ProfilePojo?

}