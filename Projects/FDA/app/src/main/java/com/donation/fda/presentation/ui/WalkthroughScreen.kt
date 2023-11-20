package com.donation.fda.presentation.ui

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.donation.fda.theme.primaryColor
import com.donation.fda.presentation.components.TextView
import com.donation.fda.presentation.ui.navigations.NavScreen
import com.donation.fda.theme.white
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.record.fda.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun IntroSliderViewScreen(navController: NavHostController) {

    val context = LocalContext.current
    val pagerState = rememberPagerState()
    val list = getListContent()

    val isSkipVisible = remember { derivedStateOf { pagerState.currentPage == 0 } }
    val isNextVisible = remember { derivedStateOf { pagerState.currentPage < list.size - 1 } }
    val isPrevVisible = remember { derivedStateOf { pagerState.currentPage > 0 } }
    val isContinues = remember { derivedStateOf { pagerState.currentPage == list.size - 1 } }

    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.background(color = white)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.75f),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically,
                count = list.size
            ) { currentPage ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = list[currentPage].image,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )

                    TextView(
                        text = list[currentPage].title,
                        style = MaterialTheme.typography.h4,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    TextView(
                        text = list[currentPage].description,
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.padding(vertical = 26.dp),
            activeColor = primaryColor
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            if (isSkipVisible.value) {
                Button(
                    onClick = {
                        navController.navigate(NavScreen.WelcomePage.route){
                            popUpTo(NavScreen.IntroSliderPage.route){
                                inclusive = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Unspecified,
                    ),
                    modifier = Modifier
                        .border(1.dp, color = primaryColor, shape = CircleShape)
                        .height(38.dp)
                ) {
                    TextView(text = "Skip", textAlign = TextAlign.Center)
                }
            }
            if (isPrevVisible.value) {
                Button(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Unspecified,
                    ),
                    modifier = Modifier
                        .border(1.dp, color = primaryColor, shape = CircleShape)
                        .height(38.dp)
                ) {
                    TextView(text = "Preview")
                }
            }
            if (isNextVisible.value && !isContinues.value) {
                Button(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                    ),
                    modifier = Modifier
                        .border(1.dp, color = primaryColor, shape = CircleShape)
                        .height(38.dp)
                ) {
                    TextView(text = "Next")
                }
            }
            if (isContinues.value) {
                Button(
                    onClick = {
                        // Navigate to the welcome screen
                      navController.navigate(NavScreen.WelcomePage.route){
                            popUpTo(NavScreen.IntroSliderPage.route){
                                inclusive = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                    ),
                    modifier = Modifier
                        .border(1.dp, color = primaryColor, shape = CircleShape)
                        .height(38.dp)
                ) {
                    TextView(text = "Continue")
                }
            }
        }
    }
}

data class HorizontalPagerContent(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
)

fun getListContent(): List<HorizontalPagerContent> {
    return listOf(
        HorizontalPagerContent(
            image = R.mipmap.img_donation_food,
            title = "Donate food",
            description = "your convenience—be it during party functions or anytime you feel inclined to contribute to helping people in need."
        ),
        HorizontalPagerContent(
            image = R.mipmap.img_pickup_food,
            title = "Pick-up food",
            description = "Select 'Pick Up Food' find the location and donate the received food to contribute to the food donation process."
        ),
        HorizontalPagerContent(
            image = R.mipmap.img_distribute_food,
            title = "Distributed food",
            description = "Food picked to go for food distribution to some people. This system can work for social services and it is not for making a profit."
        )
    )
}

