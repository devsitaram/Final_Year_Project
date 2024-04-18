package com.sitaram.foodshare.features.navigations

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.features.forgotpassword.presentation.ForgotPasswordViewScreen
import com.sitaram.foodshare.features.introSlider.IntroSliderViewScreen
import com.sitaram.foodshare.features.login.presentation.LoginViewScreen
import com.sitaram.foodshare.features.register.presentation.RegisterViewScreen
import com.sitaram.foodshare.features.welcome.WelcomeViewScreen
import com.sitaram.foodshare.features.dashboard.dashboardDonor.DonorBtnNavBar
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.AdminBtnNavBar
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.donationRating.presentation.FoodDonationRatingView
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.VolunteerBtnNavBar
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.acceptFood.FoodAcceptViewScreen
import com.sitaram.foodshare.features.dashboard.foodDetail.presentation.FoodDetailsViewScreen
import com.sitaram.foodshare.features.dashboard.googleMap.presentation.GoogleMapViewScreen
import com.sitaram.foodshare.features.dashboard.setting.manageAccount.presentation.ManageAccountScreen
import com.sitaram.foodshare.features.dashboard.setting.ngoProfile.presentation.NgoProfileViewScreen
import com.sitaram.foodshare.features.dashboard.userProfileDetails.presentation.UserDetailsViewScreen
import com.sitaram.foodshare.utils.ApiUrl
import com.sitaram.foodshare.utils.ApiUrl.Companion.FOOD_NAME
import com.sitaram.foodshare.utils.ApiUrl.Companion.FOOD_RATING
import com.sitaram.foodshare.utils.ApiUrl.Companion.KEY_ID
import com.sitaram.foodshare.utils.ApiUrl.Companion.LATITUDE
import com.sitaram.foodshare.utils.ApiUrl.Companion.LONGITUDE
import com.sitaram.foodshare.utils.ApiUrl.Companion.USER_NAME
import com.sitaram.foodshare.utils.ApiUrl.Companion.USER_ROLE

@SuppressLint("NewApi")
@Composable
fun MainNavigationViewScreen() {

    val context = LocalContext.current
    val getSharedPreferences = UserInterceptors(context)
    val getInstallDevice = getSharedPreferences.installApp()
    val accessToken = getSharedPreferences.getAccessToken()
    val userRole = getSharedPreferences.getUserRole()

    val mainNavController = rememberNavController()

    NavHost(
        navController = mainNavController,
        startDestination = if (getInstallDevice.isEmpty()) {
            NavScreen.IntroSliderPage.route
        } else {
            if (accessToken.isEmpty()) {
                NavScreen.LoginPage.route
            } else {
                when (userRole.lowercase()) {
                    "donor" -> {
                        NavScreen.DonorDashboardPage.route
                    }

                    "admin" -> {
                        NavScreen.AdminDashboardPage.route
                    }

                    else -> {
                        NavScreen.VolunteerDashboardPage.route
                    }
                }
            }
        },
        enterTransition = { // Enter transition
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(200)
            )
        },
        exitTransition = { // Exit transition
            slideOutHorizontally(
                targetOffsetX = { -it }, // Adjusted to it instead of -it
                animationSpec = tween(200)
            )
        },
        popEnterTransition = { // Pop enter transition
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(200)
            )
        },
        popExitTransition = { // Pop exit transition
            slideOutHorizontally(
                targetOffsetX = { -it }, // Adjusted to it instead of -it
                animationSpec = tween(200)
            )
        },
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        composable(NavScreen.IntroSliderPage.route) {
            IntroSliderViewScreen(mainNavController)
        }

        composable(NavScreen.WelcomePage.route) {
            WelcomeViewScreen(mainNavController)
        }

        composable(NavScreen.LoginPage.route) {
            LoginViewScreen(mainNavController)
        }

        composable(NavScreen.RegisterPage.route) {
            RegisterViewScreen(mainNavController)
        }

        composable(NavScreen.ForgotPasswordPage.route) {
            ForgotPasswordViewScreen(mainNavController)
        }

        // donor
        composable(NavScreen.DonorDashboardPage.route) {
            DonorBtnNavBar(mainNavController)
        }

        // volunteer
        composable(NavScreen.VolunteerDashboardPage.route) {
            VolunteerBtnNavBar(mainNavController)
        }

        // admin
        composable(NavScreen.AdminDashboardPage.route) {
            AdminBtnNavBar(mainNavController)
        }

        // Ngo profile
        composable(
            route = NavScreen.NgoPage.route,
            arguments = listOf(
                navArgument(USER_ROLE){
                    type = NavType.StringType
                }
            )
        ){ backStackEntry ->
            val role = backStackEntry.arguments?.getString(USER_ROLE)
            NgoProfileViewScreen(role, mainNavController)
        }

        // manage account
        composable(NavScreen.AccountManagePage.route) {
            ManageAccountScreen(mainNavController)
        }

        // Notification
//        composable(NavScreen.Notification.route){
//            NotificationViewScreen(mainNavController)
//        }

        // google map
        composable(
            route = NavScreen.GoogleMapViewPage.route,
            arguments = listOf(
                navArgument(LATITUDE) {
                    type = NavType.StringType
                },
                navArgument(LONGITUDE) {
                    type = NavType.StringType
                },
                navArgument(USER_NAME) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val latitude = backStackEntry.arguments?.getString(LATITUDE)?.toDoubleOrNull() ?: 0.0
            val longitude = backStackEntry.arguments?.getString(LONGITUDE)?.toDoubleOrNull() ?: 0.0
            val username = backStackEntry.arguments?.getString(USER_NAME) ?: ""
            GoogleMapViewScreen(mainNavController, latitude, longitude, username)
        }

        // Food Details View
        composable(
            route = NavScreen.FoodDetailViewPage.route,
            arguments = listOf(
                navArgument(KEY_ID) {
                    type = NavType.StringType
                },
                navArgument(FOOD_NAME) {
                    type = NavType.StringType
                },
                navArgument(FOOD_RATING) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(KEY_ID)?.toIntOrNull() ?: 0
            val title = backStackEntry.arguments?.getString(FOOD_NAME)
            val rating = backStackEntry.arguments?.getInt(FOOD_RATING)
            FoodDetailsViewScreen(id, title, rating, mainNavController)
        }

        composable(
            route = NavScreen.UserDetailViewPage.route,
            arguments = listOf(
                navArgument(KEY_ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(KEY_ID)?.toIntOrNull() ?: 0
            UserDetailsViewScreen(id, mainNavController)
        }

        // Accept food
        composable(
            route = NavScreen.FoodAcceptViewPage.route,
            arguments = listOf(
                navArgument(KEY_ID) {
                    type = NavType.StringType
                },
                navArgument(FOOD_NAME){
                    type = NavType.StringType
                },
                navArgument(ApiUrl.USER_EMAIL){
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(KEY_ID)?.toIntOrNull() ?: 0
            val foodName = backStackEntry.arguments?.getString(FOOD_NAME)
            val email = backStackEntry.arguments?.getString(ApiUrl.USER_EMAIL)
            FoodAcceptViewScreen(id, foodName, email, mainNavController)
        }

        // Donation Rating
        composable(route = NavScreen.CompetedFoodHistory.route,
            arguments = listOf(
                navArgument(KEY_ID) {
                    type = NavType.StringType
                },
                navArgument(FOOD_NAME) {
                    type = NavType.StringType
                },
                navArgument(ApiUrl.USER_EMAIL) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getString(KEY_ID)?.toIntOrNull() ?: 0
            val title = backStackEntry.arguments?.getString(FOOD_NAME) ?: ""
            val email = backStackEntry.arguments?.getString(ApiUrl.USER_EMAIL) ?: ""
            FoodDonationRatingView(foodId, title, email, mainNavController)
        }
    }
}
