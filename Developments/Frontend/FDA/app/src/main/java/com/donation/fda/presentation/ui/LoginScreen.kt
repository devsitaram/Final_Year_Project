package com.donation.fda.presentation.ui

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.donation.fda.presentation.ui.dashboard.donor.ScreenList
import com.donation.fda.presentation.ui.navigations.NavScreen
import com.donation.fda.presentation.ui.util.ButtonView
import com.donation.fda.presentation.ui.util.CanvasView
import com.donation.fda.presentation.ui.util.CheckboxComponent
import com.donation.fda.presentation.ui.util.DividerWithText
import com.donation.fda.presentation.ui.util.ErrorMessageDialogBox
import com.donation.fda.presentation.ui.util.InputTextFieldView
import com.donation.fda.presentation.ui.util.LottieAnimationsView
import com.donation.fda.presentation.ui.util.PasswordTextFieldView
import com.donation.fda.presentation.ui.util.TextButtonView
import com.donation.fda.presentation.ui.util.TextView
import com.donation.fda.presentation.ui.util.VectorIconView
import com.donation.fda.theme.orange
import com.donation.fda.theme.primaryColor
import com.donation.fda.theme.red
import com.donation.fda.theme.white
import com.donation.fda.theme.yellow
import com.record.fda.R

@Composable
fun LoginViewScreen(navController: NavHostController) {

    val context = LocalContext.current

//    val getSharedPreferences = ClientInterceptors(context)

    var checkedState by remember { mutableStateOf(false) }
    var isInvalidUser by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var emailEmptyValue by remember { mutableStateOf(false) }
    val isEmailEmpty by remember { derivedStateOf { email.isEmpty() } }

    var password by remember { mutableStateOf("") }
    var passwordEmptyValue by remember { mutableStateOf(false) }
    val isPasswordEmpty by remember { derivedStateOf { password.isEmpty() } }

    val onClickLogin: () -> Unit = {
        navController.navigate(NavScreen.DashboardPage.route){
            popUpTo(NavScreen.LoginPage.route){
                inclusive = true
            }
        }
//            // call viewmodel function
//        LogInViewModel.getLoginUserAuth(email, password)
//        if (userLoginResult.data?.success == true) {
//            when (userLoginResult.data?.roll) {
//                "Donor" -> {
//                    // navigate the Donor dashboard
//                    navController.navigate(NavScreen.DashboardPage.route) {
//                        popUpTo(NavScreen.LoginPage.route) {
//                            inclusive = true
//                            val editor = sharedPreferences.edit()
//                            editor.putString("accessToken", "${userLoginResult.data.result?.accessToken}").apply()
//                        }
//                    }
//                }
//
//                "Volunteer", "Farmer" -> {
//                    // navigate the Volunteer and Farmer dashboard
//                    // navigate the Donor dashboard
//                    navController.navigate(NavScreen.DashboardPage.route) {
//                        popUpTo(NavScreen.LoginPage.route) {
//                            inclusive = true
//                            val editor = sharedPreferences.edit()
//                            editor.putString("accessToken", "${userLoginResult.data.result?.accessToken}").apply()
//                        }
//                    }
//                }
//
//                "NGO" -> {
//                    // navigate the NGO dashboard
//                    navController.navigate(NavScreen.DashboardPage.route) {
//                        popUpTo(NavScreen.LoginPage.route) {
//                            inclusive = true
//                            val editor = sharedPreferences.edit()
//                            editor.putString("accessToken", "${userLoginResult.data.result?.accessToken}").apply()
//                        }
//                    }
//                }
//            }
//        } else {
//            isInvalidUser = true
//        }
    }

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
            .padding(horizontal = 8.dp)
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
                text = "Sign in to your \naccount!",
                color = Color.DarkGray,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 25.sp,
                textAlign = TextAlign.Center,
            )

            Image(
                painter = painterResource(id = R.mipmap.img_profile),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)  // Clip the image into a circle

            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            val isAnimating by remember { mutableStateOf(true) }
//            if (isAnimating) {
                LottieAnimationsView(
                    rawResource = R.raw.login_animation,
                    isAnimating = true, // isAnimating
                    speed = 0.75f,
                    modifier = Modifier.size(200.dp)
                )

//            }
            TextView(
                text = "Enter the valid email and password",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        InputTextFieldView(
            value = email,
            onValueChange = { email = it },
            leadingIcon = { VectorIconView(imageVector = Icons.Default.PersonOutline) },
            label = "Email",
            placeholder = "Enter email",
            isEmptyValue = emailEmptyValue,
            errorColor = red,
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
            errorColor = red,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButtonView(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(12.dp),
                onClick = { navController.navigate(NavScreen.ForgotPasswordPage.route) },
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
                emailEmptyValue = isEmailEmpty
                passwordEmptyValue = isPasswordEmpty
                if (!isEmailEmpty && !isPasswordEmpty) {
                    onClickLogin()
                }
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

        Spacer(modifier = Modifier.padding(top = 10.dp))

        DividerWithText(
            text = "OR", modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
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
            color = red
        )
    }
}



