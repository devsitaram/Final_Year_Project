package com.sitaram.foodshare.features

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.SendToMobile
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.SendToMobile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.sitaram.foodshare.features.dashboard.pushNotification.MyFirebaseMessagingService
import com.sitaram.foodshare.features.dashboard.setting.ngoProfile.presentation.CustomDataCardView
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.utils.UserInterfaceUtil
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.isLocationEnabled
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.isTokenExpired
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.compose.ButtonView
import java.util.Date

@Composable
fun TestingScreen() {

    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonView(btnText = "Token") {
            showToast(context, "Location on: ${isLocationEnabled(context)}")
        }
    }
}