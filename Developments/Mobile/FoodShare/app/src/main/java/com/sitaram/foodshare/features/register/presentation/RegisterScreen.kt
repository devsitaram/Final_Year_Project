package com.sitaram.foodshare.features.register.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.navigations.NavScreen
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.theme.black
import com.sitaram.foodshare.utils.compose.DividerWithTextView
import com.sitaram.foodshare.utils.compose.InputTextFieldView
import com.sitaram.foodshare.utils.compose.PasswordTextFieldView
import com.sitaram.foodshare.theme.darkGray
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.red
import com.sitaram.foodshare.theme.skyBlue
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.utils.ApiUrl
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.Validators.isValidEmailAddress
import com.sitaram.foodshare.utils.compose.ClickableTextView
import com.sitaram.foodshare.utils.compose.ButtonSize
import com.sitaram.foodshare.utils.compose.ButtonView
import com.sitaram.foodshare.utils.compose.ErrorMessageDialogBox
import com.sitaram.foodshare.utils.compose.TextFieldView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.PainterImageView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.VectorIconView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun RegisterViewScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {

    // Check The Internet Connection
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available

    val context = LocalContext.current
    val getInstance = UserInterceptors(context)
    val systemToken = getInstance.getSystemToke()
    val editor = getInstance.getPreInstEditor()
    val userRegisterResult = registerViewModel.registerState
    var message by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    var role by remember { mutableStateOf("") }
    // checkbox action
    val isOnClickButtonSheet: () -> Unit = {
        scope.launch {
            if (scaffoldState.bottomSheetState.isExpanded) {
                scaffoldState.bottomSheetState.collapse()
            } else {
                scaffoldState.bottomSheetState.expand()
            }
        }
    }

    var isBottomSheetExpanded by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = scaffoldState.bottomSheetState.isExpanded, block = {
        isBottomSheetExpanded = scaffoldState.bottomSheetState.isExpanded
    })

    if (userRegisterResult.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Unspecified),
            contentAlignment = Alignment.Center
        ) {
            ProcessingDialogView(
                title = stringResource(R.string.processing),
                body = stringResource(R.string.please_wait_for_processing)
            )
        }
    }

    LaunchedEffect(key1 = userRegisterResult.error, block = {
        if (userRegisterResult.error != null) {
            message = userRegisterResult.error
            showDialog = true
        }
    })

    LaunchedEffect(key1 = userRegisterResult.data, block = {
        if (userRegisterResult.data?.isSuccess == true) {
            showToast(context, "${userRegisterResult.data.message}")
            navController.navigate(NavScreen.LoginPage.route)
            if (systemToken.isEmpty()) {
                editor.putString("systemToken", userRegisterResult.data.systemToken).apply()
            }
        }
    })

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetShape = ShapeDefaults.ExtraLarge,
        modifier = Modifier
            .fillMaxWidth(),
        sheetContent = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {
                val userList = listOf(
                    UserList(
                        logo = painterResource(id = R.mipmap.img_donor),
                        userType = stringResource(id = R.string.donor)
                    ),
                    UserList(
                        logo = painterResource(id = R.mipmap.img_volunteer),
                        userType = stringResource(id = R.string.volunteer)
                    ),
                    UserList(
                        logo = painterResource(id = R.mipmap.img_farmer),
                        userType = stringResource(R.string.farmer)
                    ),
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(
                            onClick = { isOnClickButtonSheet.invoke() }
                        ) {
                            VectorIconView(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                tint = gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        TextView(
                            text = stringResource(R.string.choose_your_options),
                            textType = TextType.TITLE4,
                            color = textColor,
                            textAlign = TextAlign.Start,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                        )
                    }

                    // Display user list using LazyColumn
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                    ) {
                        this.items(userList) { user ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        isOnClickButtonSheet()
                                        role = user.userType
                                    }
                                    .padding(vertical = 10.dp, horizontal = 15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Card(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .size(140.dp), shape = ShapeDefaults.Large
                                ) {
                                    Image(
                                        painter = user.logo,
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = user.userType)
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        // this is the screen
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // text fields variable initialize
            var name by remember { mutableStateOf("") }
            var nameErrorValue by remember { mutableStateOf(false) }
            var nameEmptyValue by remember { mutableStateOf(false) }

            var email by remember { mutableStateOf("") }
            var emailEmptyValue by remember { mutableStateOf(false) }
            val isEmailEmpty by remember { derivedStateOf { email.isEmpty() } }
            var isValidEmail by remember { mutableStateOf(false) }

            var password by remember { mutableStateOf("") }
            var passwordEmptyValue by remember { mutableStateOf(false) }
            val isPasswordEmpty by remember { derivedStateOf { password.isEmpty() } }

            var roleEmptyValue by remember { mutableStateOf(false) }
            val isRoleEmpty by remember { derivedStateOf { role.isEmpty() } }

            val isRoleDropDown by remember { mutableStateOf(false) }

            // register button onClickAction
            val registerOnClick = {
                if (isConnected) {
                    MainScope().launch {
                        if (isValidEmailAddress(email)) {
                            registerViewModel.registerUser(email, name, role, password)
                        } else {
                            isValidEmail = true
                        }
                    }
                } else {
                    showToast(context, context.getString(R.string.no_internet_connection))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    // back to go previous page
                    IconButton(
                        onClick = {
                            navController.navigate(NavScreen.LoginPage.route) {
                                popUpTo(NavScreen.RegisterPage.route) { inclusive = true }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
                PainterImageView(
                    painterImage = painterResource(id = R.mipmap.img_register),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                TextView(
                    text = stringResource(R.string.register_your_details_on_free),
                    textType = TextType.TITLE2,
                    color = darkGray,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                InputTextFieldView(
                    value = email,
                    onValueChange = {
                        email = it
                        if (it.isNotEmpty()) {
                            emailEmptyValue = false
                            isValidEmail = false
                        }
                    },
                    label = stringResource(id = R.string.email),
                    placeholder = stringResource(id = R.string.enter_email_address),
                    isEmptyValue = emailEmptyValue,
                    isInvalidValue = isValidEmail,
                    invalidMessage = stringResource(id = R.string.enter_the_valid_email_address),
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

                InputTextFieldView(
                    value = name,
                    onValueChange = {
                        name = it
                        nameEmptyValue = false
                        nameErrorValue = false
                    },
                    label = stringResource(id = R.string.username),
                    placeholder = stringResource(R.string.enter_full_name),
                    isEmptyValue = nameEmptyValue,
                    isInvalidValue = nameErrorValue,
                    invalidMessage = stringResource(R.string.enter_the_valid_username),
                    errorColor = red,
                    leadingIcon = {
                        VectorIconView(
                            imageVector = Icons.Default.PermIdentity,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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

                TextFieldView(
                    value = role,
                    onValueChange = { role = it },
                    label = stringResource(id = R.string.role),
                    placeholder = stringResource(R.string.select_your_role),
                    isEmptyValue = roleEmptyValue,
                    readOnly = true,
                    errorColor = red,
                    leadingIcon = {
                        VectorIconView(
                            imageVector = Icons.Default.SupervisedUserCircle,
                            tint = gray,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    isOnClickButtonSheet()
                                    isRoleDropDown != isRoleDropDown
                                }
                        )
                    },
                    trailingIcon = {
                        VectorIconView(
                            imageVector = if (isRoleDropDown) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                            tint = if (isRoleDropDown) primary else gray,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    isOnClickButtonSheet()
                                    isRoleDropDown != isRoleDropDown
                                }
                        )
                    },
                    shape = ShapeDefaults.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isOnClickButtonSheet()
                            isRoleDropDown != isRoleDropDown
                        }
                        .padding(vertical = 4.dp)
                        .height(58.dp)
                )

                Spacer(modifier = Modifier.padding(top = 8.dp))
                // register button
                ButtonView(
                    onClick = {
                        nameEmptyValue = name.isEmpty()
                        emailEmptyValue = isEmailEmpty // email error message
                        passwordEmptyValue = isPasswordEmpty // password error message
                        roleEmptyValue = isRoleEmpty
                        if (name.isNotEmpty() && !isEmailEmpty && !isPasswordEmpty && !isRoleEmpty) {
                            registerOnClick()
                        }
                    },
                    btnText = stringResource(R.string.create_my_account),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    buttonSize = ButtonSize.LARGE,
                )

                DividerWithTextView(
                    text = stringResource(id = R.string.or),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextView(
                        text = stringResource(R.string.for_donor_volunteer_and_farmer_who_want_to_use_the_platform_who_have_not_email_create_account),
                        textType = TextType.SMALL_TEXT_REGULAR,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextView(
                        text = stringResource(R.string.www_gmail_com),
                        textType = TextType.SMALL_TEXT_REGULAR,
                        color = skyBlue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .wrapContentWidth()
                            .clickable {
                                val webIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    // create gail account
                                    Uri.parse(ApiUrl.NAVIGATE_TO_EMAIL)
                                )
                                context.startActivity(webIntent)
                            }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextView(
                        text = stringResource(R.string.already_have_an_account),
                        textType = TextType.BASE_TEXT_REGULAR,
                        color = textColor,
                    )
                    ClickableTextView(
                        annotatedText = stringResource(R.string.login_now),
                        color = primary,
                        textType = TextType.BASE_TEXT_SEMI_BOLD,
                        onClick = {
                            navController.navigate(NavScreen.LoginPage.route) // "Login/${selectedText}"
                        },
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 4.dp)
                    )
                }
            }
        }

        // Transparent overlay to cover the screen when BottomSheet is expanded
        if (isBottomSheetExpanded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(black.copy(alpha = 0.5f))
                    .clickable(onClick = { isOnClickButtonSheet.invoke() })
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

data class UserList(
    val logo: Painter,
    val userType: String
)