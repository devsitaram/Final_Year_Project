package com.sitaram.foodshare.features.zTesting

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.Composable
import com.sitaram.foodshare.features.pushNotification.MyFirebaseMessagingService
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.compose.ButtonView

@Composable
fun TestViewScreen() {

    val context = LocalContext.current
    val fcmToke = MyFirebaseMessagingService().getTokenInstance()

    ButtonView(btnText = "Token") {
        showToast(context, "Token, ${fcmToke.result}")
        Log.d("token", fcmToke.result)
    }
}