package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.acceptFood

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.foodDetail.presentation.FoodDetailViewModel
import com.sitaram.foodshare.features.dashboard.foodDetail.presentation.ViewFoodDetails
import com.sitaram.foodshare.features.navigations.BtnNavScreen
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.theme.backgroundLayoutColor
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.compose.DisplayErrorMessageView
import com.sitaram.foodshare.utils.compose.DividerView
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.ProgressIndicatorView
import com.sitaram.foodshare.utils.compose.SuccessMessageDialogBox
import com.sitaram.foodshare.utils.compose.TopAppBarView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun FoodAcceptViewScreen(
    foodId: Int,
    title: String?,
    email: String?,
    navController: NavController,
    mainNavController: NavHostController,
    foodDetailViewModel: FoodDetailViewModel = hiltViewModel(),
) {

    // Check The Internet Connection
    val connection by NetworkObserver.connectivityState()
    val isNetworkConnected = connection === NetworkObserver.ConnectionState.Available

    val context = LocalContext.current
    val interceptors = UserInterceptors(context)
    val userId = interceptors.getUserId()
    val username = interceptors.getUserName()

    LaunchedEffect(key1 = foodDetailViewModel, block = {
        foodDetailViewModel.getFoodDetailState(foodId)
    })

    val getFoodDetails = foodDetailViewModel.foodDetailState
    val getFoodAcceptState = foodDetailViewModel.foodAcceptState
    var isSuccess by remember { mutableStateOf(false) }
    val isCompletedDonation by remember { mutableStateOf(true) }

    if (getFoodDetails.isLoading) {
        ProgressIndicatorView()
    }

    if(getFoodAcceptState.isLoading) {
        ProcessingDialogView()
    }

    if (getFoodDetails.error != null || getFoodAcceptState.error != null) {
        DisplayErrorMessageView(
            text = getFoodAcceptState.error ?: getFoodAcceptState.error ?: stringResource(R.string.food_not_found),
            vectorIcon = if (foodDetailViewModel.isRefreshing) null else Icons.Default.Refresh,
            onClick = { foodDetailViewModel.getSwipeToRefresh() }
        )
    }

    LaunchedEffect(key1 = getFoodAcceptState.data, block = {
        if (getFoodAcceptState.data?.isSuccess == true) {
            isSuccess = true
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBarView(
            title = title ?: "Food Details",
            color = textColor,
            backgroundColor = white,
            leadingIcon = Icons.Default.ArrowBackIosNew,
            modifier = Modifier
                .shadow(4.dp)
                .fillMaxWidth(),
            onClickLeadingIcon = {
                navController.navigateUp()
            }
        )
        DividerView(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)

        if (!isNetworkConnected) {
            NetworkIsNotAvailableView()
        } else {
            getFoodDetails.data?.let {
                if (isCompletedDonation) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(backgroundLayoutColor)
                            .padding(bottom = 56.dp),
                    ) {
                        ViewFoodDetails(
                            food = it,
                            context = context,
                            isBtnVisible = true,
                            onClickViewMap = {
                                mainNavController.navigate("GoogleMapView/${it.latitude.toString()}/${it.longitude.toString()}/${it.username}")
                            },
                            onClickViewProfile = {
                                mainNavController.navigate("UserDetailView/${it.userId}")
                            },
                            status = it.status,
                            onClickAcceptBtn = {
                                MainScope().launch {
                                    foodDetailViewModel.getAcceptFood(foodId, "Pending", userId, username)
                                }
                            },
                            onClickCompletedBtn = {
                                mainNavController.navigate("CompetedFoodHistory/${foodId}/${it.foodName}/${email}")
                            }
                        )
                    }
                }
            }
        }
    }
    
    if (isSuccess){
        SuccessMessageDialogBox(
            title = stringResource(R.string.food_accepted),
            descriptions = getFoodAcceptState.data?.message,
            onDismiss = {
                isSuccess = false
                navController.navigate(BtnNavScreen.Home.route)
            }
        )
    }
}