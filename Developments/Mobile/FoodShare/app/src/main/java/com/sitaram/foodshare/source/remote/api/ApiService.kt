package com.sitaram.foodshare.source.remote.api

import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.data.pojo.ReportPojo
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data.UsersPojo
import com.sitaram.foodshare.features.dashboard.home.data.FoodPojo
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import com.sitaram.foodshare.features.login.domain.LoginModelDTO
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.domain.RequestModelDAO
import com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.data.DonorHistoryPojo
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationModelDAO
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.data.pojo.PendingPojo
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.ReportDTO
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.donationRating.domain.FoodDonateRatingDto
import com.sitaram.foodshare.features.dashboard.foodDetail.data.pojo.FoodDetailsPojo
import com.sitaram.foodshare.features.dashboard.foodDetail.data.pojo.FoodUpdatePojo
import com.sitaram.foodshare.features.dashboard.foodDetail.domain.FoodModelDAO
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.history.data.pojo.FoodHistoryPojo
import com.sitaram.foodshare.features.dashboard.notification.data.NotificationPojo
import com.sitaram.foodshare.features.dashboard.setting.manageAccount.domain.DeleteAccount
import com.sitaram.foodshare.features.dashboard.setting.manageAccount.domain.UpdatePassword
import com.sitaram.foodshare.features.dashboard.profile.data.ProfilePojo
import com.sitaram.foodshare.features.dashboard.profile.domain.ProfileModelDAO
import com.sitaram.foodshare.features.dashboard.setting.ngoProfile.data.NgoProfilePojo
import com.sitaram.foodshare.features.dashboard.setting.ngoProfile.data.NumberOfDataPojo
import com.sitaram.foodshare.features.forgotpassword.domain.ForgotPasswordDAO
import com.sitaram.foodshare.features.login.data.pojo.LoginAuthPojo
import com.sitaram.foodshare.features.register.data.RegisterPojo
import com.sitaram.foodshare.features.register.domain.RegisterModelDAO
import com.sitaram.foodshare.utils.ApiUrl
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

/**
 * This is the interface ApiService
 * There have multiple function for room database API to server call
 */
interface ApiService {

    // Login authentication
    @POST(ApiUrl.LOGIN_USER)
    suspend fun getLoginUserAuth(@Body loginModel: LoginModelDTO?): LoginAuthPojo?

    // Register user
    @POST(ApiUrl.REGISTER_USER)
    suspend fun registerUser(@Body registerModelDAO: RegisterModelDAO?): RegisterPojo?

    @GET(ApiUrl.LOG_OUT)
    suspend fun getLogOut(): ResponsePojo?

    // GET Food Details
    @Multipart
    @POST(ApiUrl.NEW_FOOD_POST)
    suspend fun newFoodDonation(
        @PartMap dynamicParamsMap: Map<String, @JvmSuppressWildcards RequestBody>?,
        @Part imagePart: MultipartBody.Part?,
    ): ResponsePojo?

    // Get latest foodDetail
    @GET(ApiUrl.GET_NEW_FOOD)
    suspend fun getNewFoodDetails(): FoodPojo?

    // Get history
    @GET(ApiUrl.GET_ALL_HISTORY)
    suspend fun getFoodHistory(): FoodHistoryPojo?

    // Get user profile
    @GET(ApiUrl.USER_PROFILE)
    suspend fun getUserProfiles(): ProfilePojo?

    // Update profile
    @PATCH(ApiUrl.UPDATE_PROFILE)
    suspend fun updateProfile(
        @Query("id") id: Int?,
        @Body request: ProfileModelDAO?,
    ): ProfilePojo?

    // Update profile image
    @Multipart
    @PATCH(ApiUrl.UPDATE_PROFILE_IMAGE)
    suspend fun updateProfilePicture(
        @Part("id") id: RequestBody?,
        @Part image: MultipartBody.Part?,
    ): ProfilePojo?

    // New user account verify
    @POST(ApiUrl.USER_ACCOUNT_ACTIVATE)
    suspend fun userAccountVerify(@Body request: RequestModelDAO?): UsersPojo?

    // Get All Users
    @GET(ApiUrl.GET_ALL_USER)
    suspend fun getAllTypesOfUser(): UsersPojo?

    // Email verify
    @GET(ApiUrl.EMAIL_VERIFY)
    suspend fun getVerifyEmail(
        @Query("query") email: String?,
    ): ResponsePojo?

    // Forgot password
    @PATCH(ApiUrl.UPDATE_PASSWORD)
    suspend fun forgotPassword(@Body request: ForgotPasswordDAO?): ResponsePojo?

    // Delete Or Deactivate account
    @PATCH(ApiUrl.DELETE_ACCOUNT)
    suspend fun deleteAccount(@Body request: DeleteAccount?): ResponsePojo?

    // Change password
    @PATCH(ApiUrl.UPDATE_PASSWORD)
    suspend fun updatePassword(@Body request: UpdatePassword?): ResponsePojo?

    // Accept food
    @POST(ApiUrl.ACCEPT_FOOD)
    suspend fun acceptFood(@Body foodModelDAO: FoodModelDAO?): ResponsePojo?

    // Get pending food
    @GET(ApiUrl.GET_PENDING_FOOD)
    suspend fun getPendingFood(
        @Query("user_id") userId: Int?,
        @Query("status") status: String?,
    ): PendingPojo?

    // Food donation rating
    @PATCH(ApiUrl.COMPLETED_FOOD_RATING)
    suspend fun donationRating(
        @Body foodDonateRatingDto: FoodDonateRatingDto?,
    ): ResponsePojo?

    // Donor history
    @GET(ApiUrl.GET_HISTORY_OF_DONOR)
    suspend fun getDonorHistory(@Query("id") id: Int?): DonorHistoryPojo?

    // Update food
    @PATCH(ApiUrl.UPDATE_FOOD)
    suspend fun getUpdateDonateFoodDetails(
        @Query("food_id") foodId: Int?,
        @Body donationModelDAO: DonationModelDAO?,
    ): FoodUpdatePojo?

    // Update food image
    @Multipart
    @PATCH(ApiUrl.UPDATE_FOOD_IMAGE)
    suspend fun getUpdateDonateFoodImage(
        @Part("id") id: RequestBody?,
        @Part image: MultipartBody.Part?,
    ): FoodUpdatePojo?

    // Delete food
    @PATCH(ApiUrl.DELETE_FOOD)
    suspend fun getDeleteFood(
        @Query("food_id") id: Int?,
        @Query("username") username: String?,
    ): ResponsePojo?

    // Complaint and report details with admin
    @POST(ApiUrl.REPORT_USER)
    suspend fun getReportToUser(@Body reportDTO: ReportDTO?): ResponsePojo?

    // Get report/compliant
    @GET(ApiUrl.GET_REPORT)
    suspend fun getReportDetails(): ReportPojo?

    // Report/compliant verify
    @PATCH(ApiUrl.VERIFY_REPORT)
    suspend fun setReportVerify(
        @Body request: RequestModelDAO?
    ): ResponsePojo?

    // Delete history
    @PATCH(ApiUrl.DELETE_HISTORY)
    suspend fun getDeleteHistory(
        @Query("history_id") historyId: Int?,
        @Query("username") username: String?,
    ): ResponsePojo?

    // Device FCM token register
    @POST(ApiUrl.REGISTER_FCM_DEVICE_TOKEN)
    suspend fun deviceFcmTokenSave(
        @Query("user_id") userId: Int?,
        @Query("token") fcmToken: String?,
        @Query("created_by") userName: String?,
    ): ResponsePojo?

    // Ngo profile
    @GET(ApiUrl.NGO_PROFILE)
    suspend fun getNgoProfile(): NgoProfilePojo?

    // Get the system data
    @GET(ApiUrl.NUMBER_OF_DATA)
    suspend fun getNumberOfData(@Query("role") role: String?): NumberOfDataPojo?

    // Notifications
    @GET(ApiUrl.GET_NOTIFICATION)
    suspend fun getNotification(): NotificationPojo?

//    @PATCH(ApiUrl.VIEW_NOTIFICATION)
//    suspend fun viewNotification(): ResponsePojo?

    @GET(ApiUrl.GET_USER)
    suspend fun getUserById(@Query("id") userId: Int): ProfilePojo?

    @GET(ApiUrl.GET_FOOD)
    suspend fun getFoodDetails(@Query("id") foodId: Int?): FoodDetailsPojo?

}