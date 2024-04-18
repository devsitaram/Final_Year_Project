package com.sitaram.foodshare.features.dashboard.profile.data

import android.content.Context
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.features.dashboard.profile.domain.ProfileRepository
import com.sitaram.foodshare.features.dashboard.profile.domain.ProfileModelDAO
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ProfileRepositoryImpl(private val apiService: ApiService, private val context: Context) : ProfileRepository {

    override suspend fun getUserProfile(): ProfilePojo? {
        return apiService.getUserProfiles()
    }

    // Update profile details
    override suspend fun updateProfile(userId: Int?, updateProfile: ProfileModelDAO?): ProfilePojo? {
        return apiService.updateProfile(userId, updateProfile)
    }

    override suspend fun updateProfilePicture(userId: Int?, imageFile: File?): ProfilePojo? {
        val requestFile = imageFile?.asRequestBody("image/*".toMediaTypeOrNull())
        val image = imageFile.let { MultipartBody.Part.createFormData("image", imageFile?.name ?: "user",  requestFile ?: error("File is null")) }
        val userIdRequestBody = userId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        return apiService.updateProfilePicture(userIdRequestBody, image)
    }
}