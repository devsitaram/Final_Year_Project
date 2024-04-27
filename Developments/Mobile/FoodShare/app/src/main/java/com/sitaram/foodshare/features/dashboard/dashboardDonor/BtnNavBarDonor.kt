package com.sitaram.foodshare.features.dashboard.dashboardDonor

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.filled.AddToPhotos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.presentation.HistoryDonorScreen
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.presentation.FoodPostViewScreen
import com.sitaram.foodshare.features.dashboard.home.presentation.HomeViewScreen
import com.sitaram.foodshare.features.dashboard.profile.presentation.ProfileViewScreen
import com.sitaram.foodshare.features.dashboard.setting.presentation.SettingViewScreen
import com.sitaram.foodshare.features.navigations.BtnNavScreen
import com.sitaram.foodshare.features.navigations.NavScreen
import com.sitaram.foodshare.features.navigations.donatePages
import com.sitaram.foodshare.theme.backgroundLayoutColor
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.VectorIconView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DonorBtnNavBar(mainNavController: NavHostController) {

    val navController = rememberNavController()
    var isPostActive by remember { mutableStateOf(false) } // Remember the state
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp)),
                cutoutShape = CircleShape,
                elevation = 0.dp,
                backgroundColor = backgroundLayoutColor,
            ) {
                BottomNav(
                    navController = navController,
                    onClick = {
                        isPostActive = false
                    },
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                contentColor = primary,
                onClick = {
                    NavScreen.FoodPostPage.route.let {
                        navController.navigate(it) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        isPostActive = true
                    }
                    NavScreen.FoodPostPage.route.let {
                        navController.navigate(it)
                    }
                },
                backgroundColor = if (isPostActive) {
                    primary
                } else {
                    gray // Change to your desired color
                }
            ) {
                VectorIconView(
                    imageVector = Icons.Filled.AddToPhotos,
                    modifier = Modifier.size(25.dp),
                    contentDescription = "Add icon",
                    tint = white
                )
            }
        }
    ) {
        DonorNavHostScreen(navController, mainNavController)
    }
}

@SuppressLint("ResourceAsColor")
@Composable
fun BottomNav(navController: NavController, onClick: () -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    BottomNavigation(
        backgroundColor = backgroundLayoutColor,
        contentColor = primary,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp))
    ) {
        donatePages.forEach { screen ->
            BottomNavigationItem(
                modifier = Modifier.fillMaxWidth(),
                icon = {
                    screen?.icon?.let { it ->
                        VectorIconView(
                            imageVector = it,
                            modifier = Modifier.size(25.dp),
                            tint = if (currentRoute?.hierarchy?.any {
                                    it.route == screen.route
                                } == true) {
                                primary
                            } else {
                                gray
                            }
                        )
                    }
                },
                label = {
                    screen?.title?.let { it ->
                        TextView(
                            text = it,
                            textType = if (currentRoute?.hierarchy?.any { it.route == screen.route } == true) {
                                TextType.SMALL_TEXT_BOLD
                            } else {
                                TextType.SMALL_TEXT_REGULAR
                            },
                            color = if (currentRoute?.hierarchy?.any { it.route == screen.route } == true) {
                                primary
                            } else {
                                gray
                            }
                        )
                    }
                },
                selected = currentRoute?.hierarchy?.any {
                    it.route == screen?.route
                } == true,
                onClick = {
                    screen?.route?.let { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    onClick.invoke()
                }
            )
        }
    }
}

@Composable
fun DonorNavHostScreen(navController: NavHostController, mainNavController: NavHostController) {
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
        contentAlignment = Alignment.Center,
    ) {
        // home
        composable(BtnNavScreen.Home.route) {
            HomeViewScreen(mainNavController)
        }

        // History
        composable(BtnNavScreen.History.route) {
            HistoryDonorScreen(mainNavController)
        }

        // post
        composable(NavScreen.FoodPostPage.route) {
            FoodPostViewScreen(navController)
        }

        // profile
        composable(BtnNavScreen.Profile.route) {
            ProfileViewScreen()
        }

        // setting
        composable(BtnNavScreen.Setting.route) {
            SettingViewScreen(navController, mainNavController)
        }
    }
}
