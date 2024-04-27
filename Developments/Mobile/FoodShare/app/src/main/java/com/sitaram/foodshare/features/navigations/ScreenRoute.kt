package com.sitaram.foodshare.features.navigations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.sitaram.foodshare.utils.ApiUrl.Companion.FOOD_NAME
import com.sitaram.foodshare.utils.ApiUrl.Companion.FOOD_RATING
import com.sitaram.foodshare.utils.ApiUrl.Companion.KEY_ID
import com.sitaram.foodshare.utils.ApiUrl.Companion.LATITUDE
import com.sitaram.foodshare.utils.ApiUrl.Companion.LONGITUDE
import com.sitaram.foodshare.utils.ApiUrl.Companion.USER_EMAIL
import com.sitaram.foodshare.utils.ApiUrl.Companion.USER_NAME
import com.sitaram.foodshare.utils.ApiUrl.Companion.USER_ROLE

sealed class NavScreen(val route: String) {
    object IntroSliderPage : NavScreen("IntroSlider")
    object WelcomePage : NavScreen("Welcome")
    object LoginPage : NavScreen("Login")
    object RegisterPage : NavScreen("Register")
    object ForgotPasswordPage : NavScreen("ForgotPassword")
    object FoodPostPage : NavScreen("FoodPostPage")
    object DonorDashboardPage : NavScreen("Donors")
    object VolunteerDashboardPage : NavScreen("Volunteers")
    object AdminDashboardPage : NavScreen("Admin")
    object NgoPage : NavScreen("Ngo/{$USER_ROLE}")
    object AccountManagePage : NavScreen("Account")
    object Notification : NavScreen("Notification")
    object GoogleMapViewPage: NavScreen("GoogleMapView/{$LATITUDE}/{$LONGITUDE}/{$USER_NAME}")
    object FoodDetailViewPage : NavScreen("FoodDetailView/{$KEY_ID}/{$FOOD_NAME}/{$FOOD_RATING}")
    object UserDetailViewPage : NavScreen("UserDetailView/{$KEY_ID}")
    object FoodAcceptViewPage: NavScreen("FoodAcceptView/{$KEY_ID}/{$FOOD_NAME}/{$USER_EMAIL}")
    object CompetedFoodHistory: NavScreen("DonationRating/{$KEY_ID}/{$FOOD_NAME}/{$USER_EMAIL}")
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