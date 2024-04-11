package com.sitaram.foodshare.features.introSlider

import android.content.Context
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.sitaram.foodshare.features.navigations.NavScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.sitaram.foodshare.R
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.compose.ButtonSize
import com.sitaram.foodshare.utils.compose.ButtonView
import com.sitaram.foodshare.utils.compose.OutlineButtonView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun IntroSliderViewScreen(navController: NavHostController) {

    val context = LocalContext.current
    val pagerState = rememberPagerState()
    val list = getListContent(context)

    val isSkipVisible = remember { derivedStateOf { pagerState.currentPage == 0 } }
    val isNextVisible = remember { derivedStateOf { pagerState.currentPage < list.size - 1 } }
    val isPrevVisible = remember { derivedStateOf { pagerState.currentPage > 0 } }
    val isContinues = remember { derivedStateOf { pagerState.currentPage == list.size - 1 } }

    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = white)
            .padding(horizontal = 16.dp)
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
                            .padding(vertical = 16.dp)
                    )

                    TextView(
                        text = list[currentPage].title,
                        color = primary,
                        textType = TextType.TITLE1,
                        modifier = Modifier
                    )
                    TextView(
                        text = list[currentPage].description,
                        color = textColor,
                        textType = TextType.LARGE_TEXT_REGULAR,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                }
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.padding(vertical = 26.dp),
            activeColor = primary
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            if (isSkipVisible.value) {
                OutlineButtonView(
                    onClick = {
                        navController.navigate(NavScreen.WelcomePage.route) {
                            popUpTo(NavScreen.IntroSliderPage.route) { inclusive = true }
                        }
                    },
                    btnText = stringResource(R.string.skip),
                    modifier = Modifier
                        .border(1.dp, color = primary, shape = CircleShape)
                        .height(38.dp),
                    buttonSize = ButtonSize.NON
                )
            }
            if (isPrevVisible.value) {
                OutlineButtonView(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    },
                    btnText = stringResource(R.string.preview),
                    modifier = Modifier
                        .border(1.dp, color = primary, shape = CircleShape)
                        .height(38.dp)
                )
            }
            if (isNextVisible.value && !isContinues.value) {
                ButtonView(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    btnText = stringResource(R.string.next),
                    modifier = Modifier
                        .border(1.dp, color = primary, shape = CircleShape)
                        .height(38.dp),
                )
            }
            if (isContinues.value) {
                ButtonView(
                    onClick = {
                        // Navigate to the welcome screen
                        navController.navigate(NavScreen.WelcomePage.route) {
                            popUpTo(NavScreen.IntroSliderPage.route) {
                                inclusive = true
                            }
                        }
                    },
                    btnText = stringResource(R.string.continues),
                    modifier = Modifier
                        .border(1.dp, color = primary, shape = CircleShape)
                        .height(38.dp)
                )
            }
        }
    }
}

data class HorizontalPagerContent(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
)

fun getListContent(context: Context): List<HorizontalPagerContent> {
    return listOf(
        HorizontalPagerContent(
            image = R.mipmap.img_donation_food,
            title = context.getString(R.string.donate_food),
            description = context.getString(R.string.your_convenience_be)
        ),
        HorizontalPagerContent(
            image = R.mipmap.img_pickup_food,
            title = context.getString(R.string.pick_up_food),
            description = context.getString(R.string.select_pick_up_food_find_the_location)
        ),
        HorizontalPagerContent(
            image = R.mipmap.img_distribute_food,
            title = context.getString(R.string.distributed_food),
            description = context.getString(R.string.food_picked_to_go_for_food)
        )
    )
}

