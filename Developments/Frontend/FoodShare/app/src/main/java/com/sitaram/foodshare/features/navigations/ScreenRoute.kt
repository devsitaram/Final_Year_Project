package com.sitaram.foodshare.features.navigations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

const val KEY_ID = "id_key"
const val LATITUDE = "latitude"
const val LONGITUDE = "longitude"
const val USER_NAME = "username"
const val USER_EMAIL = "user_email"
const val IS_BOOLEAN = "is_boolean"
const val FOOD_NAME = "food_name"
const val FOOD_RATING = "food_rating"

sealed class NavScreen(val route: String) {
    object IntroSliderPage : NavScreen("IntroSlider")
    object WelcomePage : NavScreen("Welcome")
    object LoginPage : NavScreen("Login")
    object RegisterPage : NavScreen("Register")
    object ForgotPasswordPage : NavScreen("ForgotPassword")
    object FoodPostPage : NavScreen("FoodPostPage")
    object DonorDashboardPage : NavScreen("Donors")
    object VolunteerDashboardPage : NavScreen("Volunteers")
    object NgoDashboardPage : NavScreen("Ngo")
    object AccountManagePage : NavScreen("Account")
    object GoogleMapViewPage: NavScreen("GoogleMapView/{$LATITUDE}/{$LONGITUDE}/{$USER_NAME}")
    object FoodDetailViewPage : NavScreen("FoodDetailView/{$KEY_ID}/{$FOOD_NAME}/{$FOOD_RATING}")
    object UserDetailViewPage : NavScreen("UserDetailView/{$KEY_ID}")
    object FoodAcceptViewPage: NavScreen("FoodAcceptView/{$KEY_ID}/{$FOOD_NAME}/{$USER_EMAIL}")
    object CompetedFoodHistory: NavScreen("CompetedFoodHistory/{$KEY_ID}/{$FOOD_NAME}/{$USER_EMAIL}")
}

sealed class BtnNavScreen(val route: String, val title: String, val icon: ImageVector?) {
    object Home : BtnNavScreen("home", "Home", Icons.Default.Home)
    object History : BtnNavScreen("history", "History", Icons.Default.History)
    object Pending : BtnNavScreen("pending", "Pending", Icons.Default.DoneAll)
    object AllUser : BtnNavScreen("users", "AllUser", Icons.Default.Groups)
    object Profile : BtnNavScreen("profile", "Profile", Icons.Default.Person)
    object Setting : BtnNavScreen("setting", "Setting", Icons.Default.Settings)
}

// donor items for bottom nav
val donatePages = listOf(
    BtnNavScreen.Home,
    BtnNavScreen.History,
    null,
    BtnNavScreen.Profile,
    BtnNavScreen.Setting
)

// volunteer & farmer items for bottom nav
val volunteerPages = listOf(
    BtnNavScreen.Home,
    BtnNavScreen.Pending,
    BtnNavScreen.History,
    BtnNavScreen.Profile,
    BtnNavScreen.Setting
)

// ngo items for bottom nav
val adminPages = listOf(
    BtnNavScreen.Home,
    BtnNavScreen.AllUser,
    BtnNavScreen.History,
    BtnNavScreen.Profile,
    BtnNavScreen.Setting
)