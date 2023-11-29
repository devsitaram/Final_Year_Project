package com.donation.fda.presentation.ui.dashboard.donor

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenList(val route: String){
    object Post: ScreenList("post")
}

sealed class BtnNavScreen(val route: String, val title: String?, val icon: ImageVector?) {
    object Home : BtnNavScreen("home", "Home", Icons.Default.Home)
    object History : BtnNavScreen("history", "History", Icons.Default.History)
    object Profile : BtnNavScreen("profile", "Profile", Icons.Default.Person)
    object Setting : BtnNavScreen("setting", "Setting", Icons.Default.Settings)
}

// items for bottom nav
val items = listOf(
    BtnNavScreen.Home,
    BtnNavScreen.History,
    null,
    BtnNavScreen.Profile,
    BtnNavScreen.Setting
)