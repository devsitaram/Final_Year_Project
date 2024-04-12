package com.sitaram.foodshare.features.forgotpassword.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.navigations.NavScreen
import com.sitaram.foodshare.theme.darkGray
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.utils.compose.ErrorMessageDialogBox
import com.sitaram.foodshare.utils.compose.InputTextFieldView
import com.sitaram.foodshare.utils.compose.PasswordTextFieldView
import com.sitaram.foodshare.utils.compose.SuccessMessageDialogBox
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.red
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showNotification
import com.sitaram.foodshare.utils.Validators.isValidEmailAddress
import com.sitaram.foodshare.utils.compose.ButtonSize
import com.sitaram.foodshare.utils.compose.ButtonView
import com.sitaram.foodshare.utils.compose.PainterImageView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.VectorIconView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordViewScreen(
    navController: NavHostController,
    forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val verifyEmailResult = forgotPasswordViewModel.emailVerifyState
    val updatePasswordResult = forgotPasswordViewModel.forgotPasswordState

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

    LaunchedEffect(key1 = hasNotificationPermission, block = {
        if (!hasNotificationPermission) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    })


    var isVerifyEmail by remember { mutableStateOf(false) }
    var passwordMatch by remember { mutableStateOf(false) }

    var showSuccessDialogBox by remember { mutableStateOf(false) }
    var showErrorDialogBox by remember { mutableStateOf(false) }

    var descriptions by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }
    var emailErrorValue by remember { mutableStateOf(false) }
    var emailEmptyValue by remember { mutableStateOf(false) }
    val isEmailEmpty by remember { derivedStateOf { email.isEmpty() } }

    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var newPasswordEmptyValue by remember { mutableStateOf(false) }
    var confirmPasswordEmptyValue by remember { mutableStateOf(false) }

    val isNewPasswordEmpty by remember { derivedStateOf { newPassword.isEmpty() } }
    val isConfirmPasswordEmpty by remember { derivedStateOf { confirmPassword.isEmpty() } }

    if (!isVerifyEmail) {
        val onClickResetPassword: () -> Unit = {
            if (isValidEmailAddress(email)) {
                MainScope().launch {
                    forgotPasswordViewModel.getVerifyEmail(email = email)
                }
            } else {
                emailErrorValue = true
            }
        }

        // Show processing dialog box
        if (verifyEmailResult.isLoading) {
            ProcessingDialogView(title = stringResource(R.string.email_verifying))
        }

        if (updatePasswordResult.isLoading) {
            ProcessingDialogView(title = stringResource(R.string.email_verifying))
        }

        LaunchedEffect(key1 = verifyEmailResult, key2 = updatePasswordResult) {
            if (!verifyEmailResult.isError.isNullOrEmpty()) {
                descriptions = "This $email has been suited but" + verifyEmailResult.isError // Not found
                showErrorDialogBox = true
            }

            if (!updatePasswordResult.isError.isNullOrEmpty()) {
                descriptions = updatePasswordResult.isError
                showErrorDialogBox = true
            }
        }

        LaunchedEffect(key1 = verifyEmailResult, block = {
            if (verifyEmailResult.data?.isSuccess == true) {
                isVerifyEmail = true
            }
        })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(white)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
            PainterImageView(
                painterImage = painterResource(id = R.mipmap.img_forgot_password),
                modifier = Modifier
                    .background(color = Color.White)
                    .size(240.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextView(
                    text = stringResource(id = R.string.forgot_password),
                    textType = TextType.TITLE3,
                    color = darkGray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                TextView(
                    text = stringResource(R.string.enter_the_valid_email_address),
                    textType = TextType.BASE_TEXT_REGULAR,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )

                InputTextFieldView(
                    value = email,
                    onValueChange = {
                        email = it
                        emailErrorValue = false
                        emailEmptyValue = false
                    },
                    label = stringResource(id = R.string.email),
                    placeholder = stringResource(R.string.enter_email_address),
                    isEmptyValue = emailEmptyValue,
                    isInvalidValue = emailErrorValue,
                    invalidMessage = stringResource(R.string.enter_the_valid_email_address),
                    errorColor = red,
                    leadingIcon = {
                        VectorIconView(
                            imageVector = Icons.Default.MailOutline,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .height(58.dp)
                )

                ButtonView(
                    onClick = {
                        emailEmptyValue = isEmailEmpty
                        if (!isEmailEmpty) {
                            onClickResetPassword.invoke()
                        } else {
                            emailErrorValue = false
                        }
                    },
                    btnText = stringResource(id = R.string.verify),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp),
                    buttonSize = ButtonSize.LARGE
                )
            }
        }

    } else {
        verifyEmailResult.data?.let {

            LaunchedEffect(key1 = updatePasswordResult, block = {
                if (updatePasswordResult.data?.isSuccess == true) {
                    showSuccessDialogBox = true
                }
            })

            // api call to update
            val onUpdatePassword: () -> Unit = {
                MainScope().launch {
                    forgotPasswordViewModel.setForgotPassword(
                        email = email,
                        password = confirmPassword
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(white)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = { isVerifyEmail = false }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
                PainterImageView(
                    painterImage = painterResource(id = R.mipmap.img_forgot_password),
                    modifier = Modifier
                        .background(color = Color.White)
                        .size(240.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextView(
                        text = stringResource(R.string.new_password),
                        textType = TextType.TITLE3,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    if (passwordMatch) {
                        TextView(
                            text = stringResource(id = R.string.new_and_confirm_passwords_do_not_match),
                            color = red,
                            textType = TextType.BASE_TEXT_REGULAR,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    } else {
                        TextView(
                            text = stringResource(R.string.don_t_worry_we_are),
                            textType = TextType.BASE_TEXT_REGULAR,
                            color = gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }

                    PasswordTextFieldView(
                        value = newPassword,
                        onValueChange = {
                            newPassword = it
                            newPasswordEmptyValue = false
                            passwordMatch = false
                        },
                        leadingIcon = {
                            VectorIconView(
                                imageVector = Icons.Default.LockOpen,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = stringResource(R.string.new_),
                        placeholder = stringResource(id = R.string.new_password),
                        isEmptyValue = newPasswordEmptyValue,
                        errorColor = red,
                        isInvalidValue = passwordMatch,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .height(58.dp)
                    )

                    PasswordTextFieldView(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            passwordMatch = false
                            confirmPasswordEmptyValue = false
                        },
                        leadingIcon = {
                            VectorIconView(
                                imageVector = Icons.Default.LockOpen,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = stringResource(id = R.string.confirm),
                        placeholder = stringResource(id = R.string.confirm_password),
                        isEmptyValue = confirmPasswordEmptyValue,
                        errorColor = red,
                        isInvalidValue = passwordMatch,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .height(58.dp)
                    )

                    ButtonView(
                        onClick = {
                            passwordMatch = false
                            newPasswordEmptyValue = isNewPasswordEmpty
                            confirmPasswordEmptyValue = isConfirmPasswordEmpty

                            if (!isNewPasswordEmpty && !isConfirmPasswordEmpty) {
                                if (newPassword == confirmPassword) {
                                    onUpdatePassword.invoke()
                                } else {
                                    passwordMatch = true
                                }
                            }
                        },
                        btnText = stringResource(R.string.reset_password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 30.dp),
                        buttonSize = ButtonSize.LARGE
                    )
                }
            }
        }
    }

    if (showSuccessDialogBox) {
        SuccessMessageDialogBox(
            title = stringResource(R.string.success),
            descriptions = stringResource(R.string.your_password_update_has_been_confirmed),
            onDismiss = {
                showSuccessDialogBox = false
                showNotification(context, context.getString(R.string.reset_password),
                    context.getString(
                        R.string.currently_your_password_has_been_updated
                    )
                )
                navController.navigate(NavScreen.LoginPage.route) {
                    popUpTo(NavScreen.ForgotPasswordPage.route) {
                        inclusive = true
                    }
                }
            },
            btnText = stringResource(id = R.string.okay),
            color = primary
        )
    }

    if (showErrorDialogBox) {
        ErrorMessageDialogBox(
            title = stringResource(id = R.string.error),
            descriptions = descriptions,
            onDismiss = {
                showErrorDialogBox = false
            },
            btnText = stringResource(id = R.string.okay),
            color = red
        )
    }
}