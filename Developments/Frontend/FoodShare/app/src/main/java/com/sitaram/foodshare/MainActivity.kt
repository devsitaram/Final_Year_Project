package com.sitaram.foodshare

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.sitaram.foodshare.features.navigations.MainNavigationViewScreen
import com.sitaram.foodshare.features.zTesting.TestViewScreen
import com.sitaram.foodshare.theme.FoodShareTheme
import com.sitaram.foodshare.theme.backgroundLayoutColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalCoroutinesApi::class)
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
//                    TestViewScreen()
                    MainNavigationViewScreen()
                }
            }
        }
    }
}