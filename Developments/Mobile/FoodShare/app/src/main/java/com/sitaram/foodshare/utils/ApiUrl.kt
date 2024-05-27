package com.sitaram.foodshare.utils

/**
 * This class contains all the API endpoints used in the application.
 * There have local database name and API base url found.
 * All contains value is api path
 */
class ApiUrl {

    companion object {

        // NOTIFICATION
        const val CHANNEL_ID = "notification_channel"
        const val CHANNEL_NAME = "com.sitaram.foodshare"

        // LOCAL DATABASE NAME
        const val LOCAL_DATABASE_NAME = "FoodShare"

        // API BASE URL
        const val API_BASE_URL = "https://f3c5-103-41-172-194.ngrok-free.app/"

        // API PATH
        const val LOGIN_USER = "api/authenticate/token"
        const val REGISTER_USER = "api/register/user"
        //  const val LOG_OUT = "api/logout/user"

        // PROFILE
        const val USER_PROFILE = "api/user/profile"
        const val UPDATE_PROFILE = "api/update/profile"
        const val UPDATE_PROFILE_IMAGE = "api/update/profile/image"
        const val GET_USER = "api/user"

        // USERS
        const val GET_ALL_USER = "api/all/types/user"
        const val USER_ACCOUNT_ACTIVATE = "api/account/activate"

        // FORGOT/UPDATE PASSWORD
        const val EMAIL_VERIFY = "api/email/verify"
        const val UPDATE_PASSWORD = "api/update/password"

        // DEACTIVATE ACCOUNT
        const val DELETE_ACCOUNT = "api/account/delete"

        // FOOD
        const val NEW_FOOD_POST = "api/food/new/post"
        const val GET_NEW_FOOD = "api/food/new/get"
        const val UPDATE_FOOD = "api/update/donate/food"
        const val UPDATE_FOOD_IMAGE = "api/update/food/image"
        const val ACCEPT_FOOD = "api/food/accept"
        const val GET_PENDING_FOOD = "api/food/history/status"
        const val COMPLETED_FOOD_RATING = "api/food/donate/rating"
        const val GET_HISTORY_OF_DONOR = "api/food/donation/histories/donor"
        const val GET_ALL_HISTORY = "api/food/history/all"
        const val DELETE_FOOD = "api/food/deleted"
        const val DELETE_HISTORY = "api/history/delete"
        const val GET_FOOD = "api/food"

        // COMPLAIN TO USER
        const val REPORT_USER = "api/user/report"
        const val GET_REPORT = "api/get/report"
        const val VERIFY_REPORT = "api/verify/report"

        // Ngo Profile
        const val NGO_PROFILE = "api/ngo/profile"

        // DEVICE FCM TOKEN
        const val REGISTER_FCM_DEVICE_TOKEN = "api/fcm/device/token/save"
        const val GET_NOTIFICATION = "api/get/all/notification"
        // const val VIEW_NOTIFICATION = "api/view/notification"

        // VIDEO KEY
        const val YOUTUBE_VIDEO_KEY = "AUJRuouwtGs"
        const val NUMBER_OF_DATA = "api/data/count"

        // DATA PASS KEY
        const val KEY_ID = "id_key"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val USER_NAME = "username"
        const val USER_EMAIL = "user_email"
        const val FOOD_NAME = "food_name"
        const val USER_ROLE = "user_role"
        const val FOOD_RATING = "food_rating"
        const val LOG_OUT = "api/logout/user"

        // DEFAULT IMAGE
        const val NAVIGATE_TO_EMAIL = "https://accounts.google.com/lifecycle/steps/signup/name?continue=https://mail.google.com/mail/&dsh=S1661076939:1700642031568321&flowEntry=SignUp&flowName=GlifWebSignIn&service=mail&theme=glif&TL=AHNYTITevo2Lqzffgo_ASptfEExdEk6nU3AFab3NVhDokqtgU3ET05LqLlheaVIx"
        const val PROFILE_URL = "/media/util_images/male_profile.jpg"
        const val MALE_PROFILE_URL = "/media/util_images/male_profile.jpg"
        const val FEMALE_PROFILE_URL = "/media/util_images/female_profile.jpg"
        const val FOOD_URL = "https://prints.ultracoloringpages.com/42bab3c21f818f0062fd0e9b56d499ec.png"
    }
}