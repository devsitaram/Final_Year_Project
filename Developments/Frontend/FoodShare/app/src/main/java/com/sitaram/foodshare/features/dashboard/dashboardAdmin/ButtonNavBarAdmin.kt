package com.sitaram.foodshare.features.dashboard.dashboardAdmin

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.presentation.AdminHomeViewScreen
import com.sitaram.foodshare.features.dashboard.history.presentation.HistoryViewScreen
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.presentation.UsersViewScreenNgo
import com.sitaram.foodshare.features.dashboard.profile.presentation.ProfileViewScreen
import com.sitaram.foodshare.features.dashboard.setting.SettingViewScreen
import com.sitaram.foodshare.features.navigations.BtnNavScreen
import com.sitaram.foodshare.features.navigations.adminPages
import com.sitaram.foodshare.theme.backgroundLayoutColor
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.VectorIconView


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminBtnNavBar(mainNavController: NavHostController) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        bottomBar = {
            BottomNavigation(backgroundColor = backgroundLayoutColor, contentColor = primary) {
                adminPages.forEach { screen ->
                    BottomNavigationItem(
                        modifier = Modifier
                            .background(color = white),
                        icon = {
                            screen.icon?.let { it ->
                                VectorIconView(
                                    imageVector = it,
                                    modifier = Modifier.size(25.dp),
                                    tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                        primary // Change to your desired color
                                    } else {
                                        gray
                                    }
                                )
                            }
                        },
                        label = {
                            TextView(
                                text = screen.title,
                                textType = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                    TextType.SMALL_TEXT_BOLD
                                } else {
                                    TextType.SMALL_TEXT_REGULAR
                                },
                                color = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                    primary
                                } else {
                                    gray
                                }
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            screen.let {
                                navController.navigate(it.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) {
        AdminNavHostScreen(navController = navController, mainNavController = mainNavController)
    }
}

@Composable
fun AdminNavHostScreen(navController: NavHostController, mainNavController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BtnNavScreen.Home.route,
        enterTransition = { // Enter transition
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(100)
            )
        },
        exitTransition = { // Exit transition
            slideOutHorizontally(
                targetOffsetX = { -it }, // Adjusted to it instead of -it
                animationSpec = tween(100)
            )
        },
        popEnterTransition = { // Pop enter transition
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(100)
            )
        },
        popExitTransition = { // Pop exit transition
            slideOutHorizontally(
                targetOffsetX = { -it }, // Adjusted to it instead of -it
                animationSpec = tween(100)
            )
        },
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        composable(BtnNavScreen.Home.route) {
            AdminHomeViewScreen(navController, mainNavController)
//            HomeViewScreen(navController, mainNavController)
        }

        composable(BtnNavScreen.AllUser.route) {
            UsersViewScreenNgo(mainNavController)
        }

        composable(BtnNavScreen.History.route) {
            HistoryViewScreen(mainNavController)
        }

        composable(BtnNavScreen.Profile.route) {
            ProfileViewScreen()
        }

        // setting
        composable(BtnNavScreen.Setting.route) {
            SettingViewScreen(navController, mainNavController)
        }
    }
}