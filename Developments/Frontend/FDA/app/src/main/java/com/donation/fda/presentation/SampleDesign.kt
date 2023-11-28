package com.donation.fda.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.donation.fda.data.common.ClientInterceptors
import com.donation.fda.presentation.ui.UserList
import com.donation.fda.presentation.ui.navigations.NavScreen
import com.donation.fda.presentation.ui.util.ButtonView
import com.donation.fda.presentation.ui.util.CheckboxComponent
import com.donation.fda.presentation.ui.util.DividerWithText
import com.donation.fda.presentation.ui.util.ErrorMessageDialogBox
import com.donation.fda.presentation.ui.util.ImageViewPainter
import com.donation.fda.presentation.ui.util.InputTextFieldView
import com.donation.fda.presentation.ui.util.LottieAnimationsView
import com.donation.fda.presentation.ui.util.PasswordTextFieldView
import com.donation.fda.presentation.ui.util.TextButtonView
import com.donation.fda.presentation.ui.util.TextView
import com.donation.fda.presentation.ui.util.VectorIconView
import com.donation.fda.theme.primaryColor
import com.donation.fda.theme.white
import com.record.fda.R
import kotlinx.coroutines.launch

@Composable
fun SampleUiDesign() {

    var isAnimating by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = white),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // search lottie animation
        if (isAnimating) {
            LottieAnimationsView(
                rawResource = R.raw.login_animation,
                isAnimating = isAnimating,
                speed = 0.75f,
                modifier = Modifier.size(200.dp)
            )

        }
    }
}

@Composable
fun LoginViewScreen(userTypes: String?, navController: NavHostController) {

    val context = LocalContext.current

    val getSharedPreferences = ClientInterceptors(context)
//    val getUserType = getSharedPreferences.userTypes()

//    val users by remember { mutableStateOf(if (userTypes == null || userTypes == "{user_types}") getUserType else userTypes) }

    var checkedState by remember { mutableStateOf(true) }
    var isInvalidUser by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var emailEmptyValue by remember { mutableStateOf(false) }
    val isEmailEmpty by remember { derivedStateOf { email.isEmpty() } }

    var password by remember { mutableStateOf("") }
    var passwordEmptyValue by remember { mutableStateOf(false) }
    val isPasswordEmpty by remember { derivedStateOf { password.isEmpty() } }

    val onClickLogin: () -> Unit = {
        emailEmptyValue = isEmailEmpty
        passwordEmptyValue = isPasswordEmpty
        if (!isEmailEmpty && !isPasswordEmpty) {

            // call viewmodel function
            // add access token
//            LogInViewModel.getLoginUserAuth(email, password)
//            if (userLoginResult.data?.success == true) {
//                navController.navigate(NavScreen.DashboardPage.route) {
//                    popUpTo(NavScreen.LoginPage.route) {
//                        inclusive = true
//                        val editor = sharedPreferences.edit()
//                        editor.putString("accessToken", "${userLoginResult.data.result?.accessToken}").apply()
//                    }
//                }
//            } else {
//                isInvalidUser = true
//            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            TextView(
                text = "Sign in to your account",
                color = Color.DarkGray,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 32.sp,
                modifier = Modifier.padding(top = 5.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.mipmap.img_profile),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            TextView(
                text = "Enter your valid email and password",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        InputTextFieldView(
            value = email,
            onValueChange = { email = it },
            leadingIcon = { VectorIconView(imageVector = Icons.Default.PersonOutline) },
            label = "Email",
            placeholder = "Enter email",
            isEmptyValue = emailEmptyValue,
            errorColor = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        PasswordTextFieldView(
            value = password,
            onValueChange = { password = it },
            leadingIcon = { VectorIconView(imageVector = Icons.Default.LockOpen) },
            label = "Password",
            placeholder = "Enter password",
            isEmptyValue = passwordEmptyValue,
            errorColor = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButtonView(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(12.dp),
                onClick = { /*ScreenList.ForgotPassword.route*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Unspecified
                ),
                text = "Forgot Your Password?",
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = primaryColor
                )
            )
        }

        ButtonView(
            onClick = {
                onClickLogin()
            },
            text = "Log In",
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
            ),
            textStyle = TextStyle(
                color = white,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )

        // check box
        CheckboxComponent(
            checkedState = checkedState,
            onClick = { checkedState = !checkedState },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        )

        Spacer(modifier = Modifier.padding(top = 15.dp))

        DividerWithText(
            text = "OR", modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextView(
                text = "Don't have an account?",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.width(5.dp))
            TextView(
                text = "Register Now !",
                color = primaryColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 20.sp,
                modifier = Modifier.clickable { navController.navigate(NavScreen.RegisterPage.route) }
            )
        }
    }

    if (isInvalidUser) {
        ErrorMessageDialogBox(
            title = "Error",
            descriptions = "Your username or password is invalid. Please try to again or click on Forgot Your Password? below.",
            onDismiss = {
                isInvalidUser = false
            },
            btnText = "Okay",
            color = Color.Red
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SampleView() {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    var useRoll by remember { mutableStateOf("") }
    // checkbox action
    val chooseOnClick: () -> Unit = {
        scope.launch {
            if (scaffoldState.bottomSheetState.isExpanded) {
                scaffoldState.bottomSheetState.collapse()
            } else {
                scaffoldState.bottomSheetState.expand()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
    ) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp,
            sheetShape = ShapeDefaults.ExtraLarge,
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally),
            sheetContent = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                ) {

                    val userList = listOf(
                        UserList(
                            logo = painterResource(id = R.mipmap.img_donor),
                            userType = "Donors"
                        ),
                        UserList(
                            logo = painterResource(id = R.mipmap.img_farmer),
                            userType = "Farmers"
                        ),
                        UserList(
                            logo = painterResource(id = R.mipmap.img_volunteer),
                            userType = "Volunteers"
                        ),
                        UserList(
                            logo = painterResource(id = R.mipmap.img_ngo_logo),
                            userType = "NOGs"
                        )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(
                                onClick = { chooseOnClick() },
                                modifier = Modifier.wrapContentSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp),
                                    tint = Color.Gray
                                )
                            }
                        }

                        TextView(
                            text = "Login Your Account",
                            style = MaterialTheme.typography.h5,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        )

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
                                            chooseOnClick()
                                            useRoll = user.userType
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
                // register screen
                RegisterViewScreen(
                    userRoll = useRoll,
                    userRollOnChange = { useRoll = it },
                    chooseOnClick = { chooseOnClick() }
                )
            }
        }
    }
}

@Composable
fun RegisterViewScreen(
    userRoll: String,
    userRollOnChange: (String) -> Unit,
    chooseOnClick: () -> Unit
) {
    val context = LocalContext.current

    // text fields variable initialize
    var name by remember { mutableStateOf("") }
    var nameErrorValue by remember { mutableStateOf(false) }
    var nameEmptyValue by remember { mutableStateOf(false) }
    val isNameEmpty by remember { derivedStateOf { name.isEmpty() } }

    var email by remember { mutableStateOf("") }
    var emailErrorValue by remember { mutableStateOf(false) }
    var emailEmptyValue by remember { mutableStateOf(false) }
    val isEmailEmpty by remember { derivedStateOf { email.isEmpty() } }

    var password by remember { mutableStateOf("") }
    var passwordEmptyValue by remember { mutableStateOf(false) }
    val isPasswordEmpty by remember {
        derivedStateOf {
            password.isEmpty()
        }
    }

    // register button onClickAction
    val registerOnClick = {
        nameEmptyValue = isNameEmpty // name error message
        emailEmptyValue = isEmailEmpty // email error message
        passwordEmptyValue = isPasswordEmpty // password error message

        if (!isNameEmpty && !isEmailEmpty && !isPasswordEmpty) {

//            val isSuccess = registerViewModel.registerDetails(name, email, password, userRoll, context)
//            if (isSuccess) {
//                Toast.makeText(context, "Register success", Toast.LENGTH_SHORT).show()
//                navController.navigate(NavScreen.LoginPage.route) // navigate
//                editor.putString("userTypes", "selectedText").apply()
//            } else {
//                emailErrorMessage = true
//            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            // back to login page
            IconButton(
                onClick = {

                }
            ) {
                Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null)
            }
        }
        ImageViewPainter(painterImage = painterResource(id = R.mipmap.img_register))
        TextView(
            text = "Register your details on FREE!",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                lineHeight = 30.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        InputTextFieldView(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            placeholder = "Enter email address",
            isEmptyValue = emailEmptyValue,
            errorValue = emailErrorValue,
            invalidMessage = "Enter the valid email address",
            errorColor = Color.Red,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )

        InputTextFieldView(
            value = name,
            onValueChange = { name = it },
            label = "Username",
            placeholder = "Enter full name",
            isEmptyValue = nameEmptyValue,
            errorValue = nameErrorValue,
            invalidMessage = "Enter the valid username",
            errorColor = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )

        PasswordTextFieldView(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "Enter password",
            isEmptyValue = passwordEmptyValue,
            errorColor = Color.Red,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        OutlinedTextField(
            value = userRoll,
            onValueChange = { userRollOnChange(it) },
            label = { TextView(text = "Roll") },
            placeholder = { TextView(text = "Select your roll")},
            readOnly = true,
            trailingIcon = {
                IconButton(
                    onClick = { chooseOnClick() }
                ) {
                    Column(
                        modifier = Modifier.wrapContentWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChangeCircle,
                            contentDescription = null,
                            tint =  primaryColor
                        )
                        TextView(
                            text = "Select",
                            fontStyle = FontStyle.Normal,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )

        Spacer(modifier = Modifier.padding(top = 30.dp))
        // register button
        ButtonView(
            onClick = { registerOnClick() },
            text = "Create My account",
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = white,
                fontWeight = FontWeight.SemiBold
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.padding(top = 15.dp))
        DividerWithText(
            text = "OR",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextView(
                text = "For Donor, Volunteer and Farmer who want to use the platform who have not email, create account:",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 15.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 10.dp
                    )
            )
            TextView(
                text = "www.gmail.com",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 15.sp,
                    color = Color.Blue,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .wrapContentWidth()
                    .clickable {
                        val webIntent = Intent(
                            Intent.ACTION_VIEW,
                            // create gail account
                            Uri.parse("https://accounts.google.com/lifecycle/steps/signup/name?continue=https://mail.google.com/mail/&dsh=S1661076939:1700642031568321&flowEntry=SignUp&flowName=GlifWebSignIn&service=mail&theme=glif&TL=AHNYTITevo2Lqzffgo_ASptfEExdEk6nU3AFab3NVhDokqtgU3ET05LqLlheaVIx")
                        )
                        context.startActivity(webIntent)
                    }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextView(
                text = "Already Have An Account?",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp
                ),
                modifier = Modifier.padding(end = 5.dp)
            )
            TextView(
                modifier = Modifier
                    .wrapContentWidth()
                    .clickable {
                        /** navController.navigate(NavScreen.LoginPage.route)*/ // "Login/${selectedText}"
                    },
                text = "Login Now",
                color = primaryColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 24.sp
            )
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
//@Composable
//fun RegisterViewScreen(navController: NavHostController) {
//
//    val context = LocalContext.current
//
//    // dropdown items variable initialize
//    val users = arrayOf("Donor", "Volunteer", "Farmer", "NGOs")
//    var expanded by remember { mutableStateOf(false) }
//    var selectedText by remember { mutableStateOf(users[0]) }
//
//    // text fields variable initialize
//    var name by remember { mutableStateOf("") }
//    var nameErrorValue by remember { mutableStateOf(false) }
//    var nameEmptyValue by remember { mutableStateOf(false) }
//    val isNameEmpty by remember { derivedStateOf { name.isEmpty() } }
//
//    var email by remember { mutableStateOf("") }
//    var emailErrorValue by remember { mutableStateOf(false) }
//    var emailEmptyValue by remember { mutableStateOf(false) }
//    val isEmailEmpty by remember { derivedStateOf { email.isEmpty() } }
//
//    var phoneNum by remember { mutableStateOf("") }
//    var phoneNumErrorValue by remember { mutableStateOf(false) }
//    var phoneNumEmptyValue by remember { mutableStateOf(false) }
//    val isPhoneNumEmpty by remember { derivedStateOf { phoneNum.isEmpty() } }
//
//    var dob by remember { mutableStateOf("") }
//    var dobErrorValue by remember { mutableStateOf(false) }
//    var dobEmptyValue by remember { mutableStateOf(false) }
//    val isDobEmpty by remember { derivedStateOf { dob.isEmpty() } }
//
//    var password by remember { mutableStateOf("") }
//    var passwordEmptyValue by remember { mutableStateOf(false) }
//    val isPasswordEmpty by remember {
//        derivedStateOf {
//            password.isEmpty()
//        }
//    }
//
//    // register button onClickAction
//    val onClickAction = {
//        nameEmptyValue = isNameEmpty // name error message
//        emailEmptyValue = isEmailEmpty // email error message
//        phoneNumEmptyValue = isPhoneNumEmpty // email error message
//        dobEmptyValue = isDobEmpty // dob error message
//        passwordEmptyValue = isPasswordEmpty // password error message
//
//        if (!isNameEmpty && !isEmailEmpty && !isPasswordEmpty) {
//            phoneNumErrorValue = phoneNum.length != 10
//            dobErrorValue = dob.length != 10
////            val isSuccess = registerViewModel.registerDetails(name, email, password, context)
////            if (isSuccess) {
////                Toast.makeText(context, "Register success", Toast.LENGTH_SHORT).show()
////                navController.navigate(NavScreen.LoginPage.route) // navigate
////                editor.putString("userTypes", "selectedText").apply()
////            } else {
////                emailErrorMessage = true
////            }
//        }
//    }
//
//    Surface(modifier = Modifier.fillMaxSize()) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 10.dp, end = 10.dp)
//                .verticalScroll(rememberScrollState()),
//            verticalArrangement = Arrangement.Top,
//        ) {
////            Spacer(modifier = Modifier.padding(top = 30.dp))
////            ImageViewPainter(painterImage = painterResource(id = R.mipmap.img_profile))
//
//            Spacer(modifier = Modifier.padding(top = 30.dp))
//            TextView(
//                text = "Register your details on FREE!",
//                style = TextStyle(
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    fontFamily = FontFamily.SansSerif,
//                    color = Color.Black,
//                    lineHeight = 30.sp
//                ),
//                modifier = Modifier.padding(8.dp)
//            )
//
//            Spacer(modifier = Modifier.padding(top = 30.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Start
//            ) {
//                VectorIconView(
//                    imageVector =
//                    when (selectedText) {
//                        "Donor" -> Icons.Default.AccountCircle
//                        "Volunteer" -> Icons.Default.AccountCircle
//                        "Farmer" -> Icons.Default.AccountCircle
//                        else -> Icons.Default.AddHomeWork
//                    },
//                    tint = primaryColor,
//                    modifier = Modifier.size(70.dp)
//                )
//                // Dropdown options
//                ExposedDropdownMenuBox(
//                    expanded = expanded,
//                    onExpandedChange = {
//                        expanded = !expanded
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(5.dp)
//                ) {
//                    OutlinedTextField(
//                        value = selectedText,
//                        onValueChange = { selectedText = it },
//                        readOnly = true,
//                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//                        modifier = Modifier.menuAnchor()
//                    )
//
//                    ExposedDropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = { expanded = false }
//                    ) {
//                        users.forEach { item ->
//                            DropdownMenuItem(
//                                onClick = {
//                                    selectedText = item
//                                    expanded = false
//                                    Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
//                                }
//                            ) {
//                                TextView(text = item)
//                            }
//                        }
//                    }
//                }
//            }
//
//            InputTextFieldView(
//                value = name,
//                onValueChange = { name = it },
//                label = "Username",
//                placeholder = "Enter full name",
//                isEmptyValue = nameEmptyValue,
//                errorValue = nameErrorValue,
//                invalidMessage = "Enter the valid username",
//                errorColor = Color.Red,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(5.dp)
//            )
//
//            InputTextFieldView(
//                value = email,
//                onValueChange = { email = it },
//                label = "Email",
//                placeholder = "Enter email address",
//                isEmptyValue = emailEmptyValue,
//                errorValue = emailErrorValue,
//                invalidMessage = "Enter the valid email address",
//                errorColor = Color.Red,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(5.dp)
//            )
//
//            InputTextFieldView(
//                value = phoneNum,
//                onValueChange = {
//                    val newPhoneNumber = it.take(10).filter { char -> char.isDigit() }
//                    phoneNum = newPhoneNumber
//                },
//                label = "Phone Number",
//                placeholder = "Enter phone no",
//                isEmptyValue = phoneNumEmptyValue,
//                isInvalidValue = phoneNumErrorValue,
//                invalidMessage = "Enter the valid phone number",
//                errorColor = Color.Red,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(5.dp)
//            )
//
//            InputTextFieldView(
//                value = dob,
//                onValueChange = {
//                    // Apply the filter to allow only digits and limit to 10 characters
//                    val newDob = it.take(10).filter { char -> char.isDigit() }
//                    dob = when {
//                        newDob.length <= 2 -> newDob
//                        newDob.length <= 4 -> "${newDob.substring(0, 2)}-${newDob.substring(2)}"
//                        else -> "${newDob.substring(0, 2)}-${newDob.substring(2, 4)}-${newDob.substring(4)}"
//                    }
//                },
//                label = if (selectedText == "NGOs") "Establish date" else "Date of Birth",
//                placeholder = "dd-mm-YYYY",
//                isEmptyValue = dobEmptyValue,
//                isInvalidValue = dobErrorValue,
//                invalidMessage = "Enter the valid DOB",
//                errorColor = Color.Red,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(5.dp)
//            )
//
//            PasswordTextFieldView(
//                value = password,
//                onValueChange = { password = it },
//                label = "Password",
//                placeholder = "Enter password",
//                isEmptyValue = passwordEmptyValue,
//                errorColor = Color.Red,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(5.dp)
//            )
//
//            Spacer(modifier = Modifier.padding(top = 30.dp))
//            ButtonView(
//                onClick = { onClickAction() },
//                text = "Create My account",
//                textStyle = TextStyle(
//                    fontSize = 16.sp,
//                    color = white,
//                    fontWeight = FontWeight.SemiBold
//                ),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = primaryColor,
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            )
//
//            Spacer(modifier = Modifier.padding(top = 15.dp))
//            DividerWithText(
//                text = "OR",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 5.dp)
//            )
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 10.dp, bottom = 15.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                TextView(
//                    text = "For Donor, Volunteer and Farmer who want to use the platform who have not email, create account:",
//                    style = TextStyle(
//                        fontSize = 12.sp,
//                        fontStyle = FontStyle.Normal,
//                        fontWeight = FontWeight.Normal,
//                        lineHeight = 15.sp,
//                        color = Color.DarkGray,
//                        textAlign = TextAlign.Center
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(
//                            horizontal = 10.dp
//                        )
//                )
//                TextView(
//                    text = "www.gmail.com",
//                    style = TextStyle(
//                        fontSize = 12.sp,
//                        fontStyle = FontStyle.Normal,
//                        fontWeight = FontWeight.Normal,
//                        lineHeight = 15.sp,
//                        color = Color.Blue,
//                        textAlign = TextAlign.Center
//                    ),
//                    modifier = Modifier
//                        .wrapContentWidth()
//                        .clickable {
//                            val webIntent = Intent(
//                                Intent.ACTION_VIEW,
//                                // create gail account
//                                Uri.parse("https://accounts.google.com/lifecycle/steps/signup/name?continue=https://mail.google.com/mail/&dsh=S1661076939:1700642031568321&flowEntry=SignUp&flowName=GlifWebSignIn&service=mail&theme=glif&TL=AHNYTITevo2Lqzffgo_ASptfEExdEk6nU3AFab3NVhDokqtgU3ET05LqLlheaVIx")
//                            )
//                            context.startActivity(webIntent)
//                        }
//                )
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 30.dp),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                TextView(
//                    text = "Already Have An Account?",
//                    style = TextStyle(
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Normal,
//                        lineHeight = 20.sp
//                    ),
//                    modifier = Modifier.padding(end = 5.dp)
//                )
//                TextView(
//                    modifier = Modifier
//                        .wrapContentWidth()
//                        .clickable {
//                            navController.navigate(NavScreen.LoginPage.route) // "Login/${selectedText}"
//                        },
//                    text = "Login Now",
//                    color = primaryColor,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    lineHeight = 24.sp
//                )
//            }
//        }
//    }
//}

//Spacer(modifier = Modifier.padding(top = 15.dp))
//TextButtonWithImageIcon(
//painter = painterResource(id = R.drawable.img_google),
//buttonText = "Sing up with Google",
//style = TextStyle(
//fontSize = 14.sp,
//fontWeight = FontWeight.Normal,
//color = Color.Black,
//lineHeight = 24.sp
//),
//onClick = { }
//)
//
//Spacer(modifier = Modifier.padding(top = 10.dp))
//TextButtonWithImageIcon(
//painter = painterResource(id = R.drawable.img_facebook),
//buttonText = "Sing up with Facebook",
//style = TextStyle(
//fontSize = 14.sp,
//fontWeight = FontWeight.Normal,
//color = Color.Black,
//lineHeight = 24.sp,
//),
//onClick = {}
//)

//@Composable
//fun RegisterViewScreen(userTypes: String?, navController: NavHostController) {
//    var username by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//
//    var checkedState by remember { mutableStateOf(true) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .verticalScroll(rememberScrollState()),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Start
//        ) {
//            TextView(
//                text = "Sign in to your account",
//                color = Color.DarkGray,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.SemiBold,
//                lineHeight = 32.sp,
//                modifier = Modifier.padding(top = 5.dp)
//            )
//        }
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(30.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = painterResource(id = R.mipmap.img_profile),
//                contentDescription = null,
//                modifier = Modifier.size(150.dp)
//            )
//            TextView(
//                text = if (checkedState) userTypes.toString() else "User",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.SemiBold,
//                modifier = Modifier.padding(top = 10.dp)
//            )
//        }
//
//        InputTextFieldView(
//            value = username,
//            onValueChange = { username = it },
//            leadingIcon = { VectorIconView(imageVector = Icons.Default.PersonOutline) },
//            label = "Username",
//            placeholder = "Enter username",
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        )
//
//        PasswordTextFieldView(
//            value = password,
//            onValueChange = { password = it },
//            leadingIcon = { VectorIconView(imageVector = Icons.Default.LockOpen) },
//            label = "Password",
//            placeholder = "Enter password",
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        )
//
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
//            TextButtonView(
//                modifier = Modifier
//                    .wrapContentWidth()
//                    .padding(12.dp),
//                onClick = { /*ScreenList.ForgotPassword.route*/ },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Unspecified
//                ),
//                text = "Forgot Your Password?",
//                textStyle = TextStyle(
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    color = primaryColor
//                )
//            )
//        }
//
//        ButtonView(
//            onClick = { }, text = "Log In",
//            colors = ButtonDefaults.buttonColors(
//                containerColor = primaryColor,
//            ),
//            textStyle = TextStyle(
//                color = white,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.SemiBold
//            ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 12.dp)
//        )
//
//        // check box
//        CheckboxComponent(
//            checkedState = checkedState,
//            onClick = { checkedState = !checkedState },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 5.dp)
//        )
//
//        Spacer(modifier = Modifier.padding(top = 15.dp))
//
//        DividerWithText(
//            text = "OR", modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        )
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            TextView(
//                text = "Already have an account?",
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Normal,
//                lineHeight = 20.sp,
//                color = Color.DarkGray
//            )
//            Spacer(modifier = Modifier.width(5.dp))
//            TextView(
//                text = "Login Now",
//                color = primaryColor,
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold,
//                lineHeight = 20.sp,
//                modifier = Modifier.clickable {
//                    navController.navigate(NavScreen.LoginPage.route)
//                }
//            )
//        }
//    }
//}