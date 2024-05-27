package com.sitaram.foodshare.features.login.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.pushNotification.MyFirebaseMessagingService
import com.sitaram.foodshare.features.login.data.pojo.Authentication
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.features.navigations.NavScreen
import com.sitaram.foodshare.utils.compose.CheckboxView
import com.sitaram.foodshare.utils.compose.ErrorMessageDialogBox
import com.sitaram.foodshare.utils.compose.InputTextFieldView
import com.sitaram.foodshare.utils.compose.PasswordTextFieldView
import com.sitaram.foodshare.theme.darkGray
import com.sitaram.foodshare.theme.orange
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.red
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.theme.yellow
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.extractUserDetailsFromToken
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.Validators.isValidEmailAddress
import com.sitaram.foodshare.utils.compose.ClickableTextView
import com.sitaram.foodshare.utils.compose.ButtonSize
import com.sitaram.foodshare.utils.compose.ButtonView
import com.sitaram.foodshare.utils.compose.CanvasView
import com.sitaram.foodshare.utils.compose.DividerWithTextView
import com.sitaram.foodshare.utils.compose.LottieAnimationsView
import com.sitaram.foodshare.utils.compose.PainterImageView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.VectorIconView
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun LoginViewScreen(
    navController: NavHostController,
    logInViewModel: LoginViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    // Request for notification permission
    var hasNotificationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasNotificationPermission = isGranted
    }

    // Check The Internet Connection

    val getInstance = UserInterceptors(context)
    val editor = getInstance.getPreInstEditor()
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available

    val userLoginResult = logInViewModel.signInState
    var message by remember { mutableStateOf("") }

    var checkedState by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var emailEmptyValue by remember { mutableStateOf(false) }
    var isNotValidEmail by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var passwordEmptyValue by remember { mutableStateOf(false) }
    val isPasswordEmpty by remember { derivedStateOf { password.isEmpty() } }

    // Device fcm token
    val deviceToken = getInstance.getFcmDeviceToke()
    val fcmDeviceToken = MyFirebaseMessagingService().getTokenInstance()

    val onClickLogin: () -> Unit = {
        if (isValidEmailAddress(email)) {
            if (isConnected) {
                logInViewModel.getLoginUserAuth(email, password)
            } else {
                showToast(context, context.getString(R.string.no_internet_connection))
            }
        } else {
            isNotValidEmail = true
        }
    }

    // Fetch emailPreference inside the LaunchedEffect
    LaunchedEffect(checkedState) {
        val emailPreference = getInstance.getRememberEmail()
        if (email.isNotEmpty() && emailPreference != email && isValidEmailAddress(email)) {
            editor.putString("email", email).apply()
        }
        email = if (checkedState && emailPreference.isNotEmpty()) emailPreference else email
    }

    if (userLoginResult.isLoading) {
        ProcessingDialogView()
    }

    LaunchedEffect(key1 = userLoginResult.error, block = {
        if (userLoginResult.error != null) {
            message = userLoginResult.error
            showDialog = true
        }
    })

    // Use extracted user details
    LaunchedEffect(
        key1 = userLoginResult.data,
        block = {
            userLoginResult.data?.let { response ->
                if (response.isSuccess == true) {
                    extractUserDetailsFromToken(response.accessToken)?.let { auth ->
                        when (auth.role?.lowercase()) {
                            "donor" -> {
                                // navigate the Donor dashboard
                                navController.navigate(NavScreen.DonorDashboardPage.route) {
                                    popUpTo(NavScreen.LoginPage.route) {
                                        inclusive = true
                                    }
                                }
                            }

                            "admin" -> {
                                // navigate the Admin dashboard
                                navController.navigate(NavScreen.AdminDashboardPage.route) {
                                    popUpTo(NavScreen.LoginPage.route) {
                                        inclusive = true
                                    }
                                }
                            }

                            // navigate the Volunteer dashboard
                            else -> {
                                navController.navigate(NavScreen.VolunteerDashboardPage.route) {
                                    popUpTo(NavScreen.LoginPage.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                        // save the user authentication
                        val userAuthResponse = Authentication(
                            id = auth.id,
                            username = auth.username,
                            email = auth.email,
                            role = auth.role,
                            accessToken = response.accessToken,
                            profile = auth.profile
                        )
                        editor.putString("authentication", Gson().toJson(userAuthResponse)).apply()
                        showToast(context, response.message)
                        if (deviceToken.isEmpty()) {
                            logInViewModel.setDeviceFcmToken(
                                userId = auth.id,
                                fcmToken = fcmDeviceToken.result,
                                userName = auth.username
                            )
                            editor.putString("fcmDeviceToken", deviceToken).apply()
                        }
                    }

                    // Notification permission
                    if (!hasNotificationPermission) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Row(horizontalArrangement = Arrangement.End) {
            CanvasView(
                modifier = Modifier.size(60.dp),
                left = 1000f, top = 50f,
                radius = 550f,
                color = orange
            )
        }
        Row(horizontalArrangement = Arrangement.Start) {
            CanvasView(
                modifier = Modifier.size(70.dp),
                left = 250f, top = 50f,
                radius = 350f,
                color = yellow
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextView(
                text = stringResource(R.string.sign_in_to_your_account),
                color = white,
                textAlign = TextAlign.Center,
                textType = TextType.TITLE3
            )

            PainterImageView(
                painter = painterResource(id = R.mipmap.img_profile),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)  // Clip the image into a circle
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimationsView(
                rawResource = R.raw.login_animation,
                isAnimating = true,
                speed = 0.75f,
                stopAtProgress = 0.96f,
                modifier = Modifier.size(200.dp)
            )

            TextView(
                text = stringResource(R.string.enter_the_valid_email_and_password),
                textType = TextType.LARGE_TEXT_BOLD,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        InputTextFieldView(
            value = email,
            onValueChange = {
                email = it
                if (it.isNotEmpty()) {
                    isNotValidEmail = false
                    emailEmptyValue = false
                }
            },
            leadingIcon = {
                VectorIconView(
                    imageVector = Icons.Default.MailOutline,
                    modifier = Modifier.size(20.dp)
                )
            },
            label = stringResource(R.string.email),
            placeholder = stringResource(R.string.enter_email),
            isEmptyValue = emailEmptyValue,
            isInvalidValue = isNotValidEmail,
            invalidMessage = stringResource(R.string.invalid_email_address),
            errorColor = red,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .height(58.dp)
        )

        PasswordTextFieldView(
            value = password,
            onValueChange = {
                password = it
                passwordEmptyValue = false
            },
            leadingIcon = {
                VectorIconView(
                    imageVector = Icons.Default.LockOpen,
                    modifier = Modifier.size(20.dp)
                )
            },
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.enter_password),
            isEmptyValue = passwordEmptyValue,
            errorColor = red,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .height(58.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            ClickableTextView(
                annotatedText = stringResource(id = R.string.forgot_password),
                onClick = { navController.navigate(NavScreen.ForgotPasswordPage.route) },
                textType = TextType.BUTTON_TEXT_REGULAR,
                modifier = Modifier.wrapContentSize()
            )
        }

        ButtonView(
            onClick = {
                emailEmptyValue = email.isEmpty()
                passwordEmptyValue = isPasswordEmpty
                if (email.isNotEmpty() && !isPasswordEmpty) {
                    onClickLogin.invoke()
                }
            },
            btnText = stringResource(id = R.string.sign_in),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            buttonSize = ButtonSize.LARGE
        )

        // check box
        CheckboxView(
            text = stringResource(R.string.remember_me),
            checked = checkedState,
            onCheckedChange = { checkedState = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
        )

        DividerWithTextView(
            text = stringResource(R.string.or),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextView(
                text = stringResource(R.string.don_t_have_an_account),
                color = darkGray,
                textType = TextType.BASE_TEXT_REGULAR
            )
            ClickableTextView(
                annotatedText = stringResource(R.string.register_now),
                color = primary,
                textType = TextType.BASE_TEXT_SEMI_BOLD,
                onClick = {
                    navController.navigate(NavScreen.RegisterPage.route)
                },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 4.dp)
            )
        }
    }

    if (showDialog) {
        ErrorMessageDialogBox(
            title = stringResource(R.string.error),
            descriptions = message,
            onDismiss = { showDialog = !showDialog },
            btnText = stringResource(R.string.okay),
            color = red,
        )
    }
}