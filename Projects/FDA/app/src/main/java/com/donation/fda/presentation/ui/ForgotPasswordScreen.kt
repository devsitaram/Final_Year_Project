package com.donation.fda.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.donation.fda.presentation.ui.navigations.NavScreen
import com.donation.fda.presentation.ui.util.ImageViewPainter
import com.donation.fda.presentation.ui.util.InputTextFieldView
import com.donation.fda.presentation.ui.util.TextView
import com.donation.fda.theme.primaryColor
import com.donation.fda.theme.white
import com.record.fda.R

@Composable
fun ForgotPasswordViewScreen(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var emailErrorValue by remember { mutableStateOf(false) }
    var emailEmptyValue by remember { mutableStateOf(false) }
    val isEmailEmpty by remember { derivedStateOf { email.isEmpty() } }

    val onClickResetPassword: () -> Unit = {
        emailEmptyValue = isEmailEmpty
        if (!isEmailEmpty) {

        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.navigate(NavScreen.LoginPage.route) }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }
        ImageViewPainter(
            painterImage = painterResource(id = R.mipmap.img_forgot_password),
            modifier = Modifier
                .background(color = Color.White)
                .size(250.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextView(text = "Forgot Password?")

            TextView(text = "Don't worry! We are here to help you. Enter your email address below to reset your password.")

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
}