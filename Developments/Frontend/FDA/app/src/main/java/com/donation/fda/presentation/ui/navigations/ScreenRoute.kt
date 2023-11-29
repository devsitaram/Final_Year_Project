package com.donation.fda.presentation.ui.navigations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.donation.fda.data.common.Constants.USER_TYPES

sealed class NavScreen(val route: String) {
    object IntroSliderPage: NavScreen("IntroSlider")
    object WelcomePage: NavScreen("Welcome")
    object LoginPage: NavScreen("Login")
    object RegisterPage: NavScreen("Register")
    object ForgotPasswordPage: NavScreen("ForgotPassword")
    object DashboardPage: NavScreen("Dashboard")
}

//sealed class BtnNavScreen(var icon: ImageVector, val route: String) {
//    object HomeScreen: BtnNavScreen(icon = Icons.Default.Home,"Home")
//    object RecordScreen : BtnNavScreen(icon = Icons.Default.EditNote,"Note")
//    object SearchScreen: BtnNavScreen(icon = Icons.Default.Search,"Search")
//    object ProfileScreen: BtnNavScreen(icon = Icons.Default.PersonOutline,"Profile")
//}