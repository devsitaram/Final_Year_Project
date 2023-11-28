package com.donation.fda.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.donation.fda.presentation.ui.navigations.NavScreen
import com.donation.fda.presentation.ui.util.ErrorMessageDialogBox
import com.donation.fda.presentation.ui.util.ImageViewPainter
import com.donation.fda.presentation.ui.util.InputTextFieldView
import com.donation.fda.presentation.ui.util.PasswordTextFieldView
import com.donation.fda.presentation.ui.util.SuccessMessageDialogBox
import com.donation.fda.presentation.ui.util.TextView
import com.donation.fda.presentation.ui.util.VectorIconView
import com.donation.fda.theme.green
import com.donation.fda.theme.primaryColor
import com.donation.fda.theme.red
import com.donation.fda.theme.white
import com.record.fda.R

@Composable
fun ForgotPasswordViewScreen(navController: NavHostController) {

    var isVerifyEmail by remember { mutableStateOf(false) }
    var isUpdate by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    if (!isVerifyEmail) {
        var email by remember { mutableStateOf("") }
        val emailErrorValue by remember { mutableStateOf(false) }
        var emailEmptyValue by remember { mutableStateOf(false) }
        val isEmailEmpty by remember { derivedStateOf { email.isEmpty() } }

        val onClickResetPassword: () -> Unit = {
            emailEmptyValue = isEmailEmpty
            if (!isEmailEmpty) {
                val verify = true // database call to get result
                if (verify) {
                    isVerifyEmail = true // change ui

                } else {
                    isError = true
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(white)
                .padding(8.dp),
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
            ImageViewPainter(
                painterImage = painterResource(id = R.mipmap.img_forgot_password),
                modifier = Modifier
                    .background(color = Color.White)
                    .size(240.dp)
            )
            TextView(
                text = "Forgot Password?",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Default
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextView(
                    text = "Don't worry! We are here to help you. Enter your email address below to reset your password.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Default,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                InputTextFieldView(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    placeholder = "Enter email address",
                    isEmptyValue = emailEmptyValue,
                    errorValue = emailErrorValue,
                    invalidMessage = "Enter the valid email address",
                    errorColor = red,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp)
                )

                Button(
                    onClick = {
                        onClickResetPassword()
                    },
                    colors = ButtonDefaults.buttonColors(
                        primaryColor
                    ),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 30.dp)
                ) {
                    TextView(
                        text = "Reset Password",
                        color = white,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }

    } else {

        var newPassword by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var newPasswordEmptyValue by remember { mutableStateOf(false) }
        var confirmPasswordEmptyValue by remember { mutableStateOf(false) }
        val isNewPasswordEmpty by remember { derivedStateOf { newPassword.isEmpty() } }
        val isConfirmPasswordEmpty by remember { derivedStateOf { confirmPassword.isEmpty() } }

        val onUpdatePassword = {
            newPasswordEmptyValue = isNewPasswordEmpty
            confirmPasswordEmptyValue = isConfirmPasswordEmpty
            if (!(isNewPasswordEmpty && isConfirmPasswordEmpty) && (newPassword == confirmPassword)) {
                val isSuccess = true  // database call to get result
                if (isSuccess) {
                    isUpdate = true

                } else {
                    isError = true
                }
            } else if (newPassword != confirmPassword) {
                isError = true
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(white)
                .padding(8.dp),
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
            ImageViewPainter(
                painterImage = painterResource(id = R.mipmap.img_forgot_password),
                modifier = Modifier
                    .background(color = Color.White)
                    .size(240.dp)
            )
            TextView(
                text = "Forgot Password?",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Default
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextView(
                    text = "Don't worry! We are here to help you. Enter your email address below to reset your password.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Default,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                PasswordTextFieldView(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    leadingIcon = { VectorIconView(imageVector = Icons.Default.LockOpen) },
                    label = "New",
                    placeholder = "New password",
                    isEmptyValue = newPasswordEmptyValue,
                    errorColor = red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                PasswordTextFieldView(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    leadingIcon = { VectorIconView(imageVector = Icons.Default.LockOpen) },
                    label = "Confirm",
                    placeholder = "Confirm password",
                    isEmptyValue = confirmPasswordEmptyValue,
                    errorColor = red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Button(
                    onClick = {
                        onUpdatePassword()
                    },
                    colors = ButtonDefaults.buttonColors(
                        primaryColor
                    ),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 30.dp)
                ) {
                    TextView(
                        text = "Update Password",
                        color = white,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }

    if (isUpdate){
        SuccessMessageDialogBox(
            title = "Success",
            descriptions = "Your update has been confirmed. Check your login details.",
            onDismiss = {
                isUpdate = false
                navController.navigate(NavScreen.LoginPage.route){
                    popUpTo(NavScreen.ForgotPasswordPage.route){
                        inclusive = true
                    }
                }
            },
            btnText = "Okay",
            color = green
        )
    }

    if (isError){
        ErrorMessageDialogBox(
            title = "Error",
            descriptions = "Enter the correct details. Please try again!",
            onDismiss = {
                isError = false
            },
            btnText = "Okay",
            color = red
        )
    }
}