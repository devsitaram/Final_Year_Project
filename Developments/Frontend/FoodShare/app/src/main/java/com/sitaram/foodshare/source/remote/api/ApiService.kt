package com.sitaram.foodshare.source.remote.api

import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.data.pojo.ReportPojo
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data.UsersPojo
import com.sitaram.foodshare.features.dashboard.home.data.FoodPojo
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import com.sitaram.foodshare.features.login.domain.LoginModel
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.domain.UsersModelDAO
import com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.data.DonorHistoryPojo
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationModelDAO
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.data.pojo.PendingPojo
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.ReportDTO
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.domain.HistoryCompletedDto
import com.sitaram.foodshare.features.dashboard.foodDetail.data.pojo.FoodUpdatePojo
import com.sitaram.foodshare.features.dashboard.foodDetail.domain.FoodModelDAO
import com.sitaram.foodshare.features.dashboard.history.data.pojo.FoodHistoryPojo
import com.sitaram.foodshare.features.dashboard.setting.manageAccount.domain.DeleteAccount
import com.sitaram.foodshare.features.dashboard.setting.manageAccount.domain.UpdatePassword
import com.sitaram.foodshare.features.dashboard.profile.data.ProfilePojo
import com.sitaram.foodshare.features.dashboard.profile.domain.ProfileModelDAO
import com.sitaram.foodshare.features.forgotpassword.domain.ForgotPasswordDAO
import com.sitaram.foodshare.features.login.data.pojo.LoginAuthPojo
import com.sitaram.foodshare.features.register.domain.RegisterModelDAO
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


interface ApiService {

    /**
     * Login page screen
     */
    // get authentication token
    @POST("api/authenticate/token/")
    suspend fun getLoginUserAuth(@Body loginModel: LoginModel?): LoginAuthPojo?

    @POST("api/register/user")
    suspend fun registerUser(@Body registerModelDAO: RegisterModelDAO?): ResponsePojo?

    /**
     * FoodDetails Donation page
     */
    @Multipart
    @POST("api/food/new/add")
    suspend fun newFoodDonation(
        @PartMap dynamicParamsMap: Map<String, @JvmSuppressWildcards RequestBody>?,
        @Part imagePart: MultipartBody.Part?,
    ): ResponsePojo?

    // get latest foods
    @GET("api/food/new/get")
    suspend fun getNewFoodDetails(): FoodPojo?

    /**
     * History screen page
     */
    // get history
    @GET("api/food/history/all")
    suspend fun getFoodHistory(): FoodHistoryPojo?

    /**
     * Profile screen
     */
    // get user profile
    @GET("api/user/profile/")
    suspend fun getUserProfiles(): ProfilePojo?

    // update the profile
//    @Multipart
    @PATCH("api/update/profile")
    suspend fun updateProfile(
        @Query("id") id: Int?,
        @Body request: ProfileModelDAO?,
    ): ProfilePojo?

    @Multipart
    @PATCH("api/update/profile/image/")
    suspend fun updateProfilePicture(
        @Part("id") id: RequestBody?,
        @Part image: MultipartBody.Part?,
    ): ProfilePojo?


    /**
     * All user page (Admin)
     */
    // update new user account verify
    @POST("api/account/verify/")
    suspend fun userAccountVerify(@Body request: UsersModelDAO?): UsersPojo?

    // Get All Users
    @GET("api/all/types/user/")
    suspend fun getAllTypesOfUser(): UsersPojo?

    /**
     * Forgot password page
     */
    // email verify
    @GET("api/email/verify/")
    suspend fun getVerifyEmail(
        @Query("query") email: String?,
    ): ResponsePojo?

    // forgot password
    @PATCH("api/update/password/")
    suspend fun forgotPassword(@Body request: ForgotPasswordDAO?): ResponsePojo?

    /**
     * Manage account page
     */
    // delete account
    @PATCH("api/account/delete/")
    suspend fun deleteAccount(@Body request: DeleteAccount?): ResponsePojo?

    // change password
    @PATCH("api/update/password/")
    suspend fun updatePassword(@Body request: UpdatePassword?): ResponsePojo?

    // accept food
    @POST("api/food/accept")
    suspend fun acceptFood(@Body foodModelDAO: FoodModelDAO?): ResponsePojo?

    // get Pending food
    @GET("api/food/history/status")
    suspend fun getPendingFood(
        @Query("user_id") userId: Int?,
        @Query("status") status: String?,
    ): PendingPojo?

    // Pending food completed
    @PATCH("api/food/donate/competed")
    suspend fun completedDonation(
        @Body historyCompletedDto: HistoryCompletedDto?,
    ): ResponsePojo?

    // Donor Donate history
    @GET("api/food/donation/histories/donor")
    suspend fun getDonorHistory(@Query("id") id: Int?): DonorHistoryPojo?

    @PATCH("api/update/donate/food")
    suspend fun getUpdateDonateFoodDetails(
        @Query("food_id") foodId: Int?,
        @Body donationModelDAO: DonationModelDAO?,
    ): FoodUpdatePojo?

    @Multipart
    @PATCH("api/update/food/image")
    suspend fun getUpdateDonateFoodImage(
        @Part("id") id: RequestBody?,
        @Part image: MultipartBody.Part?,
    ): FoodUpdatePojo?

    @PATCH("api/food/deleted")
    suspend fun getDeleteFood(
        @Query("food_id") id: Int?,
        @Query("username") username: String?,
    ): ResponsePojo?

    // Complaint and reportDetails with admin
    @POST("api/user/report")
    suspend fun getReportToUser(@Body reportDTO: ReportDTO?): ResponsePojo?

    @GET("api/get/report")
    suspend fun getReportDetails(): ReportPojo?

    @PATCH("api/verify/report")
    suspend fun setReportVerify(
        @Query("id") id: Int?,
        @Query("is_verify") isVerify: Boolean?,
    ): ResponsePojo?

    @PATCH("api/history/delete")
    suspend fun getDeleteHistory(
        @Query("history_id") historyId: Int?,
        @Query("username") username: String?,
    ): ResponsePojo?

    @POST("api/notifications/save/fcm/token")
    suspend fun deviceFcmTokenSave(
        @Query("user_id") userId: Int?,
        @Query("token") fcmToken: String?,
        @Query("created_by") userName: String?,
    ): ResponsePojo?
}