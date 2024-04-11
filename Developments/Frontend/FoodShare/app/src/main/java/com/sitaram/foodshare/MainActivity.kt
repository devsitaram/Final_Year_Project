package com.sitaram.foodshare

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sitaram.foodshare.features.navigations.MainNavigationViewScreen
import com.sitaram.foodshare.features.welcome.WelcomeViewScreen
import com.sitaram.foodshare.features.zTesting.TestViewScreen
import com.sitaram.foodshare.theme.FoodShareTheme
import com.sitaram.foodshare.theme.backgroundLayoutColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // A surface container using the 'background' color from the theme (ngrok http 8000)
        setContent {
            FoodShareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backgroundLayoutColor // MaterialTheme.colorScheme.background
                ) {
                    val mainNavController = rememberNavController()
                    TestViewScreen()
//                    WelcomeViewScreen(mainNavController)

//                    MainNavigationViewScreen()
                }
            }
        }
    }
}