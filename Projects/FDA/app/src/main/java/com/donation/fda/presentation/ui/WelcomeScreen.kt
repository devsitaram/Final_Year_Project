package com.donation.fda.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.donation.fda.presentation.components.ButtonView
import com.donation.fda.presentation.components.TextView
import com.donation.fda.theme.primaryColor
import com.donation.fda.theme.white
import com.record.fda.R

@Composable
fun WelcomeViewScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
            .padding(8.dp), verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = R.mipmap.img_profile),
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
                onClick = { /*TODO*/ }, text = "Sign In",
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
                onClick = { /*TODO*/ },
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
