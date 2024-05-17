package com.sitaram.foodshare.features.dashboard.setting.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MarkUnreadChatAlt
import androidx.compose.material.icons.filled.WifiProtectedSetup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.features.navigations.BtnNavScreen
import com.sitaram.foodshare.features.navigations.NavScreen
import com.sitaram.foodshare.utils.ApiUrl
import com.sitaram.foodshare.utils.compose.PainterCardView
import com.sitaram.foodshare.theme.backgroundLayoutColor
import com.sitaram.foodshare.theme.darkGray
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.UserInterfaceUtil
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.clearLocalStorage
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.compose.AsyncImageView
import com.sitaram.foodshare.utils.compose.ConfirmationDialogView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.VectorIconView
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingViewScreen(
    navController: NavHostController,
    mainNavController: NavHostController,
    settingViewModel: SettingViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val getLogout = settingViewModel.settingState
    val getSharedPreferences = UserInterceptors(context)
    val auth = getSharedPreferences.getAuthenticate()
    val getPreferenceInstance = getSharedPreferences.getPreferenceInstance()
    val version = UserInterfaceUtil.getAppVersion(context) // get the app version

    var showDialogBox by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val showSnackBar: () -> Unit = {
        coroutineScope.launch {
            snackBarHostState.showSnackbar(context.getString(R.string.nothing_update_of_system))
        }
    }

    if (getLogout.isLoading){
        ProcessingDialogView()
    }

    LaunchedEffect(getLogout.error){
        if (getLogout.error != null) {
            coroutineScope.launch {
                snackBarHostState.showSnackbar(getLogout.error)
                settingViewModel.clearMessage()
            }
        }
    }

    LaunchedEffect(getLogout.data?.isSuccess == true){
        if (getLogout.message != null) {
            mainNavController.navigate(NavScreen.LoginPage.route) {
                popUpTo(BtnNavScreen.Setting.route) {
                    inclusive = true
                }
                val editor = getPreferenceInstance.edit()
                editor?.putString("authentication", "")?.apply()
                showDialogBox = false
                showToast(context, getLogout.message) // context.getString(R.string.logout_successful)
            }
        }
    }

    Scaffold(
        snackbarHost = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp), contentAlignment = Alignment.BottomCenter
            ) {
                SnackbarHost(hostState = snackBarHostState)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 50.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(white)
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileView(
                    profileUrl = auth?.profile,
                    username = auth?.username ?: stringResource(id = R.string.user),
                    email = auth?.email ?: "user@gmail.com",
                    onClick = {
                        navController.navigate(BtnNavScreen.Profile.route) {
                            popUpTo(BtnNavScreen.Setting.route) {
                                inclusive = true
                            }
                        }
                    }
                )

                PainterCardView(
                    image = painterResource(id = R.mipmap.img_app_logo),
                    text = stringResource(R.string.food_share),
                    description = "ING Food Company",
                    color = backgroundLayoutColor
                ) {
                    // Nog profile
                    mainNavController.navigate("Ngo/${auth?.role ?: context.getString(R.string.volunteer)}")
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Divider(modifier = Modifier.height(1.dp))
                SettingsList(
                    vectorIcon = Icons.Default.AccountCircle,
                    title = stringResource(R.string.account),
                    color = darkGray
                ) {
                    // navigate the account screen
                    mainNavController.navigate(NavScreen.AccountManagePage.route)
                }

                SettingsList(
                    vectorIcon = Icons.Default.WifiProtectedSetup,
                    title = stringResource(R.string.latest_version),
                    subtitle = "v${version}",
                    color = gray,
                    background = backgroundLayoutColor,
                )

                SettingsList(
                    vectorIcon = Icons.Default.DarkMode,
                    title = stringResource(R.string.dark_mode),
                    color = gray,
                    background = backgroundLayoutColor,
                )

                SettingsList(
                    vectorIcon = Icons.Default.MarkUnreadChatAlt,
                    title = stringResource(R.string.chat),
                    color = gray,
                    background = backgroundLayoutColor,
                )

                SettingsList(
                    vectorIcon = Icons.Default.Info,
                    title = stringResource(id = R.string.information),
                    color = darkGray
                ) { showSnackBar.invoke() }

                SettingsList(
                    vectorIcon = Icons.AutoMirrored.Filled.Logout,
                    title = stringResource(R.string.log_out),
                    color = darkGray
                ) { showDialogBox = true }
            }
        }

        if (showDialogBox) {
            ConfirmationDialogView(
                title = stringResource(R.string.log_out_),
                descriptions = stringResource(R.string.are_your_sure_you_want_to_log_out),
                cancelBtnText = stringResource(id = R.string.no),
                confirmBtnText = stringResource(id = R.string.yes),
                onDismiss = { showDialogBox = false },
                onConfirm = {
                    if (clearLocalStorage(context)) {
                        mainNavController.navigate(NavScreen.LoginPage.route) {
                            popUpTo(BtnNavScreen.Setting.route) {
                                inclusive = true
                            }
//                            clearLocalStorage(context)
                            showToast(context, context.getString(R.string.logout_successful))
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileView(
    profileUrl: String? = null,
    username: String? = null,
    email: String? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { onClick?.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImageView(
                model = if(!profileUrl.isNullOrEmpty()) profileUrl else ApiUrl.PROFILE_URL,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(45.dp),
            )
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TextView(
                text = username ?: "",
                textType = TextType.LARGE_TEXT_SEMI_BOLD,
                color = primary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            TextView(
                text = email ?: "",
                textType = TextType.BASE_TEXT_REGULAR,
                color = textColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
            )
        }
    }
    Spacer(modifier = Modifier.padding(top = 8.dp))
}

@Composable
fun SettingsList(
    vectorIcon: ImageVector,
    title: String,
    subtitle: String? = null,
    color: Color = Color.Unspecified,
    background: Color = Color.Unspecified,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(background) // horizontal = 8.dp, vertical = 16.dp
            .clickable { onClick?.invoke() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        VectorIconView(
            imageVector = vectorIcon,
            tint = color,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            TextView(
                text = title,
                textType = TextType.LARGE_TEXT_REGULAR,
                color = color,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
            )
            if (!subtitle.isNullOrEmpty()) {
                TextView(
                    text = subtitle,
                    textType = TextType.BASE_TEXT_REGULAR,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = color,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
    Divider(modifier = Modifier.shadow(elevation = 0.5.dp))
}