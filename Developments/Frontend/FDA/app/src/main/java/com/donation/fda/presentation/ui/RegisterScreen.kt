package com.donation.fda.presentation.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.Surface
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddHomeWork
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.BroadcastOnHome
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.rememberBottomSheetScaffoldState
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.donation.fda.presentation.ui.navigations.NavScreen
import com.donation.fda.presentation.ui.util.ButtonView
import com.donation.fda.presentation.ui.util.DividerWithText
import com.donation.fda.presentation.ui.util.InputTextFieldView
import com.donation.fda.presentation.ui.util.PasswordTextFieldView
import com.donation.fda.presentation.ui.util.TextView
import com.donation.fda.presentation.ui.util.ImageViewPainter
import com.donation.fda.presentation.ui.util.VectorIconView
import com.donation.fda.theme.primaryColor
import com.donation.fda.theme.red
import com.donation.fda.theme.white
import com.record.fda.R
import kotlinx.coroutines.launch

//val registerViewModel = RegisterViewModel()

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RegisterViewScreen(navController: NavController) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    var useRoll by remember { mutableStateOf("") }
    // checkbox action
    val onClickAction: () -> Unit = {
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
                                onClick = { onClickAction() },
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
                            text = "Choose your options!",
                            style = MaterialTheme.typography.h5,
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
                                            onClickAction()
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
                RegisterView(
                    userRoll = useRoll,
                    userRollOnChange = { useRoll = it },
                    chooseOnClick = { onClickAction() },
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RegisterView(
    userRoll: String,
    userRollOnChange: (String) -> Unit,
    chooseOnClick: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    // text fields variable initialize
    var name by remember { mutableStateOf("") }
    val nameErrorValue by remember { mutableStateOf(false) }
    var nameEmptyValue by remember { mutableStateOf(false) }
    val isNameEmpty by remember { derivedStateOf { name.isEmpty() } }

    var email by remember { mutableStateOf("") }
    val emailErrorValue by remember { mutableStateOf(false) }
    var emailEmptyValue by remember { mutableStateOf(false) }
    val isEmailEmpty by remember { derivedStateOf { email.isEmpty() } }

    var password by remember { mutableStateOf("") }
    var passwordEmptyValue by remember { mutableStateOf(false) }
    val isPasswordEmpty by remember { derivedStateOf { password.isEmpty() } }

    val roll by remember { mutableStateOf(userRoll) }
    var rollEmptyValue by remember { mutableStateOf(false) }
    val isRollEmpty by remember { derivedStateOf { roll.isEmpty() } }

    // register button onClickAction
    val registerOnClick = {

//            val isSuccess = registerViewModel.registerDetails(name, email, password, roll, context)
//            if (isSuccess) {
//                Toast.makeText(context, "Register success", Toast.LENGTH_SHORT).show()
//                navController.navigate(NavScreen.LoginPage.route) // navigate
//                editor.putString("userTypes", "selectedText").apply()
//            } else {
//                emailErrorMessage = true
//            }
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
            // back to go previous page
            IconButton(
                onClick = {
                    navController.navigateUp()
                }
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
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
            errorColor = red,
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
            errorColor = red,
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
            errorColor = red,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)) {
            OutlinedTextField(
                value = userRoll,
                onValueChange = { userRollOnChange(it) },
                label = { TextView(text = "Roll") },
                placeholder = { TextView(text = "Select your roll") },
                readOnly = true,
                isError = rollEmptyValue,
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
                                tint = primaryColor
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
            )
            TextView(
                text = "The roll is empty!",
                style = TextStyle(color = red, textAlign = TextAlign.Start),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 1.dp)
            )
        }

        Spacer(modifier = Modifier.padding(top = 30.dp))
        // register button
        ButtonView(
            onClick = {
                Toast.makeText(context, userRoll, Toast.LENGTH_SHORT).show()
                nameEmptyValue = isNameEmpty // name error message
                emailEmptyValue = isEmailEmpty // email error message
                passwordEmptyValue = isPasswordEmpty // password error message
                rollEmptyValue = isRollEmpty
                if (!isNameEmpty && !isEmailEmpty && !isPasswordEmpty && !isRollEmpty) {
                    registerOnClick()
                }
            },
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
                text = "Already have an account?",
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

data class UserList(
    val logo: Painter,
    val userType: String
)