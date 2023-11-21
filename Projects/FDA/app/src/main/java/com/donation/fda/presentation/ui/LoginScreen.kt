package com.donation.fda.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.donation.fda.presentation.ui.navigations.NavScreen
import com.donation.fda.presentation.ui.util.ButtonView
import com.donation.fda.presentation.ui.util.CheckboxComponent
import com.donation.fda.presentation.ui.util.DividerWithText
import com.donation.fda.presentation.ui.util.InputTextFieldView
import com.donation.fda.presentation.ui.util.PasswordTextFieldView
import com.donation.fda.presentation.ui.util.TextButtonView
import com.donation.fda.presentation.ui.util.TextView
import com.donation.fda.presentation.ui.util.VectorIconView
import com.donation.fda.theme.primaryColor
import com.donation.fda.theme.white
import com.record.fda.R

@Composable
fun LoginViewScreen(userTypes: String?, navController: NavHostController) {

    val userTypes by remember { mutableStateOf(userTypes ?: "Donor") }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var checkedState by remember { mutableStateOf(true) }

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
                text = if (checkedState) userTypes.toString() else "User",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        InputTextFieldView(
            value = username,
            onValueChange = { username = it },
            leadingIcon = { VectorIconView(imageVector = Icons.Default.PersonOutline) },
            label = "Username",
            placeholder = "Enter username",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        PasswordTextFieldView(
            value = password,
            onValueChange = { password = it },
            leadingIcon = { VectorIconView(imageVector = Icons.Default.LockOpen) },
            label = "Password",
            placeholder= "Enter password",
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
            onClick = { }, text = "Log In",
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
}



