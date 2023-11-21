package com.donation.fda.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.donation.fda.presentation.ui.navigations.NavScreen
import com.donation.fda.presentation.ui.util.ButtonView
import com.donation.fda.presentation.ui.util.DividerWithText
import com.donation.fda.presentation.ui.util.InputTextFieldView
import com.donation.fda.presentation.ui.util.PasswordTextFieldView
import com.donation.fda.presentation.ui.util.TextView
import com.donation.fda.presentation.ui.util.ImageViewPainter
import com.donation.fda.theme.primaryColor
import com.donation.fda.theme.white
import com.record.fda.R

//val registerViewModel = RegisterViewModel()

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun RegisterViewScreen(navController: NavHostController) {

    val context = LocalContext.current

    // dropdown items variable initialize
    val users = arrayOf("Donor", "Volunteer", "Farmer", "NGOs")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(users[0]) }

    // text fields variable initialize
    var name by remember { mutableStateOf("") }
    var nameErrorValue by remember { mutableStateOf(false) }
    var nameEmptyValue by remember { mutableStateOf(false) }
    val isNameEmpty by remember { derivedStateOf { name.isEmpty() } }

    var email by remember { mutableStateOf("") }
    var emailErrorValue by remember { mutableStateOf(false) }
    var emailEmptyValue by remember { mutableStateOf(false) }
    val isEmailEmpty by remember { derivedStateOf { email.isEmpty() } }

    var phoneNum by remember { mutableStateOf("") }
    var phoneNumErrorValue by remember { mutableStateOf(false) }
    var phoneNumEmptyValue by remember { mutableStateOf(false) }
    val isPhoneNumEmpty by remember { derivedStateOf { phoneNum.isEmpty() } }

    var dob by remember { mutableStateOf("") }
    var dobErrorValue by remember { mutableStateOf(false) }
    var dobEmptyValue by remember { mutableStateOf(false) }
    val isDobEmpty by remember { derivedStateOf { dob.isEmpty() } }

    var password by remember { mutableStateOf("") }
    var passwordEmptyValue by remember { mutableStateOf(false) }
    val isPasswordEmpty by remember {
        derivedStateOf {
            password.isEmpty()
        }
    }

    // register button onClickAction
    val onClickAction = {
        nameEmptyValue = isNameEmpty // name error message
        emailEmptyValue = isEmailEmpty // email error message
        phoneNumEmptyValue = isPhoneNumEmpty // email error message
        dobEmptyValue = isDobEmpty // dob error message
        passwordEmptyValue = isPasswordEmpty // password error message

        if (!isNameEmpty && !isEmailEmpty && !isPasswordEmpty) {
//            val isSuccess = registerViewModel.registerDetails(name, email, password, context)
//            if (isSuccess) {
//                Toast.makeText(context, "Register success", Toast.LENGTH_SHORT).show()
//                navController.navigate(NavScreen.LoginPage.route) // navigate
//            } else {
//                emailErrorMessage = true
//            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ) {
            Spacer(modifier = Modifier.padding(top = 30.dp))
            ImageViewPainter(painterImage = painterResource(id = R.mipmap.img_profile))

            Spacer(modifier = Modifier.padding(top = 30.dp))
            TextView(
                text = "Register your details on FREE!",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Black,
                    lineHeight = 30.sp
                ),
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.padding(top = 30.dp))

            // Dropdown options
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
                modifier = Modifier.width(200.dp).padding(5.dp)
            ) {
                OutlinedTextField(
                    value = selectedText,
                    onValueChange = { selectedText = it },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    users.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedText = item
                                expanded = false
                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            }
                        ){
                            TextView(text = item)
                        }
                    }
                }
            }

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

            InputTextFieldView(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                placeholder = "Enter email address",
                isEmptyValue = emailEmptyValue,
                errorValue = emailErrorValue,
                invalidMessage = "Enter the valid email address",
                errorColor = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            InputTextFieldView(
                value = phoneNum,
                onValueChange = { phoneNum = it },
                label = "Phone Number",
                placeholder = "Enter phone no",
                isEmptyValue = phoneNumEmptyValue,
                errorValue = phoneNumErrorValue,
                invalidMessage = "Enter the valid phone number",
                errorColor = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            InputTextFieldView(
                value = dob,
                onValueChange = { dob = it },
                label = "Date of Birth",
                placeholder = "Enter DOB",
                isEmptyValue = dobEmptyValue,
                errorValue = dobErrorValue,
                invalidMessage = "Enter the valid DOB",
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            Spacer(modifier = Modifier.padding(top = 30.dp))
            ButtonView(
                onClick = { onClickAction() },
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
            DividerWithText(text = "OR", modifier = Modifier.fillMaxWidth())

            TextView(
                text = "For Teacher and Principals who want to use the platform, send an email at: learn@mysecondteacher.com",
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
                    .padding(top = 10.dp, bottom = 15.dp)
            )

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
                            navController.navigate(NavScreen.LoginPage.route) // "Login/${selectedText}"
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
}

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
