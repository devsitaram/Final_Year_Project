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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.donation.fda.presentation.ui.util.ButtonView
import com.donation.fda.presentation.ui.util.TextView
import com.donation.fda.presentation.ui.navigations.NavScreen
import com.donation.fda.theme.primaryColor
import com.donation.fda.theme.white
import com.record.fda.R
import kotlinx.coroutines.launch

@Composable
fun WelcomeViewScreen(navController: NavHostController) {

    val context = LocalContext.current
    // sharedPreferences
//    val sharedPreferences =
//        context.getSharedPreferences("food_donation_preferences", Context.MODE_PRIVATE)
//    val editor = sharedPreferences.edit()
//    editor.putString("installToken", "success").apply()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
            .padding(8.dp), verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(R.mipmap.img_app_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
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
                onClick = {
                    navController.navigate(NavScreen.LoginPage.route){
                        popUpTo(NavScreen.WelcomePage.route){
                            inclusive = true
                        }
                    }
                },
                text = "Sign In",
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
                    .height(45.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            ButtonView(
                onClick = {
                    navController.navigate(NavScreen.RegisterPage.route){
                        popUpTo(NavScreen.WelcomePage.route){
                            inclusive = true
                        }
                    }
                },
                text = "Sign Up",
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Unspecified,
                ),
                textStyle = TextStyle(
                    color = primaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = primaryColor, shape = CircleShape)
            )
        }
    }
}