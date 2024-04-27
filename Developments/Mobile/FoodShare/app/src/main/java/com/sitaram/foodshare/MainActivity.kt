package com.sitaram.foodshare

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sitaram.foodshare.features.navigations.MainNavigationViewScreen
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.theme.FoodShareTheme
import com.sitaram.foodshare.theme.backgroundLayoutColor
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.isTokenExpired
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**
 * The main activity of the application, annotated with @AndroidEntryPoint for Hilt support.
 * Inside the onCreate function, setContent is used to declare the Compose view in the hierarchy.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // A surface container using the 'background' color from the theme

        setContent {
            FoodShareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backgroundLayoutColor // MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val getPreInstance = UserInterceptors(context)
                    val systemToken = getPreInstance.getSystemToke()
                    val accessToken = getPreInstance.getAccessToken()
                    if (accessToken.isNotEmpty() && systemToken.isNotEmpty()) {
                        if (isTokenExpired(accessToken, context, systemToken)) {
                              showToast(context, getString(R.string.authentication_token_is_expired))
                        }
                    }
                    MainNavigationViewScreen()
                }
            }
        }
    }
}
