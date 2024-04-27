package com.sitaram.foodshare.features.dashboard.googleMap.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.sitaram.foodshare.R
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.helper.Permission.Companion.centerOnLocation
import com.sitaram.foodshare.helper.Permission.Companion.hasViewLocationPermission
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.isLocationEnabled
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.compose.ButtonView
import com.sitaram.foodshare.utils.compose.InformationMessageDialogBox
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.ProgressIndicatorView
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.TopAppBarView
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalPermissionsApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun GoogleMapViewScreen(
    navController: NavHostController,
    latitude: Double,
    longitude: Double,
    username: String,
    locationViewModel: GoogleMapViewModel = hiltViewModel()
) {

    // Check The Internet Connection
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available

    val context = LocalContext.current
    val viewState by locationViewModel.viewState.collectAsState()
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)

    val locationSettingsLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                locationViewModel.getLocationInfo(
                    PermissionEvent.Granted,
                    latitude,
                    longitude
                )
            } else {
                showToast(context, context.getString(R.string.location_settings_were_not_updated))
            }
        }

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(!hasViewLocationPermission(context)) {
        permissionState.launchMultiplePermissionRequest()
    }

    if (!isConnected) {
        NetworkIsNotAvailableView()
    } else {
        if (isLocationEnabled(context)) {
            when {
                permissionState.allPermissionsGranted -> {
                    LaunchedEffect(Unit) {
                        locationViewModel.getLocationInfo(PermissionEvent.Granted, latitude, longitude)
                    }
                }

                permissionState.shouldShowRationale -> {
                    RationaleAlert(
                        onDismiss = {
                            showToast(context, context.getString(R.string.please_allow_location_access))
                        }
                    ) {
                        permissionState.launchMultiplePermissionRequest()
                    }
                }

                !permissionState.allPermissionsGranted && !permissionState.shouldShowRationale -> {
                    LaunchedEffect(Unit) {
                        locationViewModel.getLocationInfo(PermissionEvent.Revoked, latitude, longitude)
                    }
                }
            }

            with(viewState) {
                when (this) {
                    LocationState.Loading -> {
                        ProgressIndicatorView()
                        if (!isLocationEnabled(context)) {
                            showToast(context, stringResource(R.string.please_enable_location))
                            locationSettingsLauncher.launch(intent)
                        }
                    }

                    LocationState.RevokedPermissions -> {
                        if (hasViewLocationPermission(context)) {
                            ProcessingDialogView()
                        }
                    }

                    is LocationState.Success -> {
                        val destLoc = LatLng(location?.latitude ?: 0.0, location?.longitude ?: 0.0)
                        val cameraState = rememberCameraPositionState()

                        LaunchedEffect(key1 = destLoc) {
                            cameraState.centerOnLocation(destLoc)
                        }

                        Column(modifier = Modifier.fillMaxSize()) {
                            TopAppBarView(
                                title = stringResource(R.string.google_map),
                                leadingIcon = Icons.Default.ArrowBackIosNew,
                                color = textColor,
                                backgroundColor = white,
                                modifier = Modifier
                                    .shadow(4.dp)
                                    .fillMaxWidth(),
                                onClickLeadingIcon = { navController.navigateUp() }
                            )
                            MainScreenView(
                                destMarker = destLoc,
                                cameraState = cameraState,
                                username = username
                            )
                        }
                    }
                }
            }
        } else {
            var showDialogBox by remember { mutableStateOf(false) }
            InformationMessageDialogBox(
                title = stringResource(R.string.location_permission),
                descriptions = stringResource(R.string.please_enable_location_services),
                btnText = stringResource(id = R.string.okay),
                onDismiss = {
                    locationSettingsLauncher.launch(intent)
                    showDialogBox = false
                }
            )
        }
    }
}

@Composable
fun MainScreenView(destMarker: LatLng, cameraState: CameraPositionState, username: String?) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraState,
            properties = MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.HYBRID,
                isTrafficEnabled = true
            )
        ) {
            Marker(
                state = MarkerState(position = destMarker),
                title = "Pick-Up Point",
                snippet = "$username (Donor)",
                draggable = true,
            )
        }
    }
}

@Composable
fun RationaleAlert(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TextView(text = "We need location permissions to use this app")
                Spacer(modifier = Modifier.height(24.dp))
                ButtonView(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    },
                    modifier = Modifier.align(Alignment.End),
                    btnText = "Ok"
                )
            }
        }
    }
}