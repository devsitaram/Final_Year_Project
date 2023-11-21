package com.donation.fda.presentation.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.donation.fda.presentation.ui.util.ButtonView
import com.donation.fda.presentation.ui.util.TextView
import com.donation.fda.presentation.ui.navigations.NavScreen
import com.donation.fda.theme.primaryColor
import com.donation.fda.theme.white
import com.record.fda.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WelcomeViewScreen(navController: NavHostController) {

    val context = LocalContext.current

    // sharedPreferences
    val sharedPreferences = context.getSharedPreferences("food_donation_preferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("installToken", "success").apply()

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    // checkbox action
    val signInOnClick: () -> Unit = {
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
                    UserList(
                        onClickAction = {
                            scope.launch {
                                if (scaffoldState.bottomSheetState.isExpanded) {
                                    // navigate to register page
                                    scaffoldState.bottomSheetState.collapse()
                                } else {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                        },
                        navController = navController,
                        editor = editor
                    )
                }
            }
        ) { innerPadding ->
            // this is the screen
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Action button
                WelcomeView(
                    signInOnClick = {
                        signInOnClick()
                    },
                    signUponClick = {
                        navController.navigate(NavScreen.RegisterPage.route) {
                            popUpTo(NavScreen.WelcomePage.route){
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun UserList(onClickAction: () -> Unit = {}, navController: NavHostController, editor: SharedPreferences.Editor) {
    var userList = listOf(
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

//    var textValues by remember { mutableStateOf("") }

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
                            onClickAction()
                            navController.navigate("Login/${user.userType}"){
                                popUpTo(NavScreen.WelcomePage.route){
                                    inclusive = true
                                    // save in user type
                                    editor.putString("userTypes", user.userType).apply()
                                }
                            }
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

@Composable
fun WelcomeView(signInOnClick: () -> Unit, signUponClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
            .padding(8.dp), verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(R.mipmap.img_profile),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(60.dp)
        )

        TextView(
            text = "Welcome",
            style = MaterialTheme.typography.h4,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        TextView(
            text = "Existing users, please log in; new users, kindly register your details to join our donation platform.",
            style = MaterialTheme.typography.subtitle1,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )

        Spacer(modifier = Modifier.padding(top = 30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalArrangement = Arrangement.Center
        ) {

            ButtonView(
                onClick = { signInOnClick() }, text = "Sign In",
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                ),
                textStyle = TextStyle(color = white, fontWeight = FontWeight.SemiBold),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            ButtonView(
                onClick = { signUponClick() },
                text = "Sign Up",
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Unspecified,
                ),
                textStyle = TextStyle(color = primaryColor, fontWeight = FontWeight.SemiBold),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = primaryColor, shape = CircleShape)
                    .height(40.dp)
            )
        }
    }
}

data class UserList(
    val logo: Painter,
    val userType: String
)