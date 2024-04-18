package com.sitaram.foodshare.features.dashboard.dashboardVolunteer

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.presentation.PendingViewScreen
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.volunteerHistory.VolunteerHistoryViewScreen
import com.sitaram.foodshare.features.dashboard.home.presentation.HomeViewScreen
import com.sitaram.foodshare.features.dashboard.profile.presentation.ProfileViewScreen
import com.sitaram.foodshare.features.dashboard.setting.presentation.SettingViewScreen
import com.sitaram.foodshare.features.navigations.BtnNavScreen
import com.sitaram.foodshare.features.navigations.volunteerPages
import com.sitaram.foodshare.theme.backgroundLayoutColor
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.VectorIconView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolunteerBtnNavBar(mainNavController: NavHostController) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            BottomNavigation(backgroundColor = backgroundLayoutColor, contentColor = primary) {
                volunteerPages.forEach { screen ->
                    BottomNavigationItem(
                        modifier = Modifier
                            .background(color = white),
                        icon = {
                            screen.icon?.let { it1 ->
                                VectorIconView(
                                    imageVector = it1,
                                    modifier = Modifier.size(20.dp),
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
                                    TextType.SMALL_TEXT_SEMI_BOLD
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
        VolunteerNavHostScreen(navController, mainNavController)
    }
}

@Composable
fun VolunteerNavHostScreen(
    navController: NavHostController,
    mainNavController: NavHostController,
) {
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
                targetOffsetX = { it }, // Adjusted to it instead of -it
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
                targetOffsetX = { it }, // Adjusted to it instead of -it
                animationSpec = tween(100)
            )
        },
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        composable(BtnNavScreen.Home.route) {
            HomeViewScreen(mainNavController)
        }

        composable(BtnNavScreen.Pending.route) {
            PendingViewScreen(mainNavController)
        }

        composable(BtnNavScreen.History.route) {
            VolunteerHistoryViewScreen(mainNavController)
        }

        composable(BtnNavScreen.Profile.route) {
            ProfileViewScreen()
        }

        // setting
        composable(BtnNavScreen.Setting.route) {
            SettingViewScreen(navController, mainNavController)
        }

//        // Accept food
//        composable(
//            route = NavScreen.FoodAcceptViewPage.route,
//            arguments = listOf(
//                navArgument(KEY_ID) {
//                    type = NavType.StringType
//                },
//                navArgument(FOOD_NAME){
//                    type = NavType.StringType
//                },
//                navArgument(USER_EMAIL){
//                    type = NavType.StringType
//                }
//            )
//        ) { backStackEntry ->
//            val id = backStackEntry.arguments?.getString(KEY_ID)?.toIntOrNull() ?: 0
//            val foodName = backStackEntry.arguments?.getString(FOOD_NAME)
//            val email = backStackEntry.arguments?.getString(USER_EMAIL)
//            FoodAcceptViewScreen(id, foodName, email, navController, mainNavController)
//        }

//        composable(route = NavScreen.CompetedFoodHistory.route,
//            arguments = listOf(
//                navArgument(KEY_ID) {
//                    type = NavType.StringType
//                },
//                navArgument(FOOD_NAME) {
//                    type = NavType.StringType
//                },
//                navArgument(USER_EMAIL) {
//                    type = NavType.StringType
//                }
//            )
//        ) { backStackEntry ->
//            val foodId = backStackEntry.arguments?.getString(KEY_ID)?.toIntOrNull() ?: 0
//            val title = backStackEntry.arguments?.getString(FOOD_NAME) ?: ""
//            val email = backStackEntry.arguments?.getString(USER_EMAIL) ?: ""
//            FoodDonationRatingView(foodId, title, email, navController)
//        }
    }
}
