package com.sitaram.foodshare.features.welcome

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.sitaram.foodshare.features.navigations.NavScreen
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.googleMap.presentation.GoogleMapViewModel
import com.sitaram.foodshare.features.dashboard.googleMap.presentation.LocationState
import com.sitaram.foodshare.features.dashboard.googleMap.presentation.PermissionEvent
import com.sitaram.foodshare.features.dashboard.googleMap.presentation.RationaleAlert
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.helper.Permission
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.compose.ButtonView
import com.sitaram.foodshare.utils.compose.OutlineButtonView
import com.sitaram.foodshare.utils.compose.ButtonSize
import com.sitaram.foodshare.utils.compose.ProgressIndicatorView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WelcomeViewScreen(
    navController: NavHostController,
    locationViewModel: GoogleMapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    // sharedPreferences
    val editor = UserInterceptors(context).getPreInstEditor()
    editor.putString("appInstallation", "success").apply()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
            .padding(8.dp), verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(R.mipmap.img_app_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
        )

        TextView(
            text = stringResource(R.string.welcome),
            textType = TextType.TITLE1,
            color = primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        TextView(
            text = stringResource(R.string.existing_users_please_log_in),
            textType = TextType.LARGE_TEXT_REGULAR,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )

        Spacer(modifier = Modifier.padding(top = 30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalArrangement = Arrangement.Center
        ) {
            ButtonView(
                onClick = {
                    navController.navigate(NavScreen.LoginPage.route) {
                        popUpTo(NavScreen.WelcomePage.route) {
                            inclusive = true
                        }
                    }
                },
                btnText = stringResource(R.string.sign_in),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                buttonSize = ButtonSize.LARGE
            )

            Spacer(modifier = Modifier.padding(8.dp))

            OutlineButtonView(
                onClick = {
                    navController.navigate(NavScreen.RegisterPage.route) {
                        popUpTo(NavScreen.WelcomePage.route) {
                            inclusive = true
                        }
                    }
                },
                btnText = stringResource(R.string.sign_up),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = primary, shape = CircleShape)
                    .height(46.dp),
                buttonSize = ButtonSize.LARGE
            )
        }
    }

    val viewState by locationViewModel.viewState.collectAsState()

    val locationSettingsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                locationViewModel.getLocationInfo(PermissionEvent.Granted, 0.0, 0.0)
            } else {
                showToast(context, "Location settings were not updated")
            }
        }

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(!Permission.hasViewLocationPermission(context)) {
        permissionState.launchMultiplePermissionRequest()
    }

    when {
        permissionState.allPermissionsGranted -> {
            LaunchedEffect(Unit) {
                locationViewModel.getLocationInfo(PermissionEvent.Granted, 0.0, 0.0)
            }
        }

        permissionState.shouldShowRationale -> {
            RationaleAlert(
                onDismiss = {
                    showToast(context, "Please allow location access.")
                }
            ) {
                permissionState.launchMultiplePermissionRequest()
            }
        }

        !permissionState.allPermissionsGranted && !permissionState.shouldShowRationale -> {
            LaunchedEffect(Unit) {
                locationViewModel.getLocationInfo(PermissionEvent.Revoked, 0.0, 0.0)
            }
        }
    }

    with(viewState) {
        when (this) {
            LocationState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ProgressIndicatorView(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.Center),
                        color = primary
                    )
                }
            }

            LocationState.RevokedPermissions -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    OutlineButtonView(
                        onClick = {
                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            locationSettingsLauncher.launch(intent)
                        },
                        btnText = "Map Permission", //stringResource(R.string.skip),
                        modifier = Modifier
                            .border(1.dp, color = primary, shape = CircleShape)
                            .height(38.dp),
                        enabled = !Permission.hasViewLocationPermission(context),
                    )
                }
            }

            else -> {

            }
        }
    }

}