package com.sitaram.foodshare.features.dashboard.setting.manageAccount.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.setting.presentation.SettingsList
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.features.navigations.BtnNavScreen
import com.sitaram.foodshare.features.navigations.NavScreen
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.red
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showLocalNotification
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.compose.ConfirmationDialogView
import com.sitaram.foodshare.utils.compose.DividerView
import com.sitaram.foodshare.utils.compose.ErrorMessageDialogBox
import com.sitaram.foodshare.utils.compose.InputDialogBoxView
import com.sitaram.foodshare.utils.compose.PasswordTextFieldView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.SuccessMessageDialogBox
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.TopAppBarView
import com.sitaram.foodshare.utils.compose.VectorIconView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Preview
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ManageAccountScreen(
    navController: NavHostController? = null,
    manageAccountViewModel: ManageAccountViewModel = hiltViewModel()
) {

    // Check The Internet Connection
    val context = LocalContext.current
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available

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

    val getUpdatePassword = manageAccountViewModel.updateAccountState
    val getDeleteAccount = manageAccountViewModel.deleteAccountState

    // share preference
    val getSharedPreferences = UserInterceptors(context)
    val authToken = getSharedPreferences.getAuthenticate()
    val editor = getSharedPreferences.getPreInstEditor()

    val email by remember { mutableStateOf(authToken?.email ?: "") }

    // show dialog box
    var showInputDialogBox by remember { mutableStateOf(false) } // temporary
    var isConfirmation by remember { mutableStateOf(false) }
    var showSuccessDialogBox by remember { mutableStateOf(false) }
    var showErrorDialogBox by remember { mutableStateOf(false) }

    // response message
    var descriptions by remember { mutableStateOf("") }

    if (getUpdatePassword.isLoading || getDeleteAccount.isLoading) {
        ProcessingDialogView()
    }

    LaunchedEffect(key1 = getUpdatePassword.isError, key2 = getDeleteAccount.isError, block = {
        if (getUpdatePassword.isError != null) {
            descriptions = context.getString(R.string.password_is_not_updated)
            showInputDialogBox = false
            showErrorDialogBox = true
        }
        if (getDeleteAccount.isError != null) {
            descriptions = "Your user account is not delete! ${getDeleteAccount.isError}"
            isConfirmation = false
            showErrorDialogBox = true
        }
    })

    LaunchedEffect(
        key1 = getUpdatePassword.data?.isSuccess,
        key2 = getDeleteAccount.data?.isSuccess,
        block = {
            // check the update password response
            getUpdatePassword.data?.isSuccess.let { response ->
                if (response == true) {
                    showSuccessDialogBox = true
                }
            }
            // check the delete response
            getDeleteAccount.data?.isSuccess.let { response ->
                if (response == true) {
                    navController?.navigate(NavScreen.LoginPage.route) {
                        popUpTo(BtnNavScreen.Setting.route) {
                            inclusive = true
                            isConfirmation = false
                            showLocalNotification(context,
                                context.getString(R.string.account_deactivation),
                                context.getString(R.string.your_account_is_permanently_deactivated)
                            )
                        }
                    }
                    editor.putString("authentication", "").apply()
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBarView(
            title = stringResource(id = R.string.account),
            leadingIcon = Icons.Default.ArrowBackIosNew,
            color = textColor,
            backgroundColor = white,
            modifier = Modifier
                .shadow(4.dp)
                .fillMaxWidth(),
            onClickLeadingIcon = { navController?.navigateUp() }
        )
        DividerView(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(white),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SettingsList(
                vectorIcon = Icons.Default.LockOpen,
                title = stringResource(R.string.change_password),
                color = textColor,
                onClick = {
                    showInputDialogBox = true
                },
            )
            SettingsList(
                vectorIcon = Icons.Default.DeleteOutline,
                title = stringResource(id = R.string.deactivate_account),
                color = textColor,
                onClick = {
                    isConfirmation = true
                }
            )
        }
    }

    if (showInputDialogBox) {

        // initialize email, password variable
        var newPassword by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        // error value
        var passwordMatch by remember { mutableStateOf(false) }

        InputDialogBoxView(
            title = stringResource(R.string.update_your_password),
            body = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    if (passwordMatch) {
                        TextView(
                            text = stringResource(R.string.new_and_confirm_passwords_do_not_match),
                            color = red,
                            textType = TextType.BASE_TEXT_REGULAR,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else {
                        TextView(
                            text = stringResource(R.string.enter_your_new_password_below_to_update_your_old_password),
                            textType = TextType.BASE_TEXT_REGULAR,
                            color = gray,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                    }

                    PasswordTextFieldView(
                        value = newPassword,
                        onValueChange = {
                            newPassword = it
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
                        },
                        leadingIcon = {
                            VectorIconView(
                                imageVector = Icons.Default.LockOpen,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = stringResource(id = R.string.confirm),
                        placeholder = stringResource(R.string.confirm_password),
                        errorColor = red,
                        isInvalidValue = passwordMatch,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .height(58.dp)
                    )
                }
            },
            onDismissRequest = { showInputDialogBox = false },
            enabled = newPassword.isNotEmpty() && confirmPassword.isNotEmpty(),
            confirmButton = {
                if (isConnected) {
                    passwordMatch = false
                    if (newPassword.isNotEmpty() && confirmPassword.isNotEmpty()) {
                        if (newPassword == confirmPassword) {
                            MainScope().launch {
                                manageAccountViewModel.updatePassword(email = email, newPassword = newPassword)
                                showInputDialogBox = false
                            }
                        } else {
                            passwordMatch = true
                        }
                    }
                } else {
                    showToast(context, context.getString(R.string.no_internet_connection))
                }
            }
        )
    }

    // error message dialog
    if (showErrorDialogBox) {
        ErrorMessageDialogBox(
            title = stringResource(id = R.string.error),
            descriptions = descriptions,
            onDismiss = {
                showErrorDialogBox = false
                descriptions = ""
            },
            btnText = stringResource(id = R.string.okay),
            color = red
        )
    }

    // success message dialog box
    if (showSuccessDialogBox) {
        SuccessMessageDialogBox(
            title = stringResource(R.string.success),
            descriptions = stringResource(R.string.your_password_update_has_been_confirmed),
            onDismiss = {
                showSuccessDialogBox = false
            },
            btnText = stringResource(id = R.string.okay),
            color = primary
        )
    }

    if (isConfirmation) {
        ConfirmationDialogView(
            title = stringResource(R.string.deactivate_account),
            confirmBtnText = stringResource(R.string.deactivate),
            descriptions = stringResource(R.string.are_your_sure_your_user_account_is_permanently_deactivate),
            onDismiss = { isConfirmation = false },
            onConfirm = {
                if (isConnected) {
                    MainScope().launch {
                        manageAccountViewModel.setDeleteAccount(email)
                    }
                } else {
                    showToast(context, context.getString(R.string.no_internet_connection))
                }
            }
        )
    }
}