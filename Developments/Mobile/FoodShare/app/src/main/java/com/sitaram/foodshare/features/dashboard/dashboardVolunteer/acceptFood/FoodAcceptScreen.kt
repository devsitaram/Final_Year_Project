package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.acceptFood

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.navigation.NavHostController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.foodDetail.presentation.ViewFoodDetails
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.theme.backgroundLayoutColor
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.isLocationEnabled
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
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
import androidx.activity.result.contract.ActivityResultContracts
import android.provider.Settings
import com.sitaram.foodshare.features.navigations.NavScreen
import com.sitaram.foodshare.utils.compose.ImageDialogView

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun FoodAcceptViewScreen(
    foodId: Int,
    title: String?,
    email: String?,
    mainNavController: NavHostController,
    foodAcceptViewModel: FoodAcceptViewModel = hiltViewModel(),
) {

    // Check The Internet Connection
    val connection by NetworkObserver.connectivityState()
    val isNetworkConnected = connection === NetworkObserver.ConnectionState.Available

    val context = LocalContext.current
    val interceptors = UserInterceptors(context)
    val userId = interceptors.getUserId()
    val username = interceptors.getUserName()

    val getFoodDetails = foodAcceptViewModel.foodDetailState
    val getFoodAcceptState = foodAcceptViewModel.foodAcceptState
    var isSuccess by remember { mutableStateOf(false) }
    val isCompletedDonation by remember { mutableStateOf(true) }
    var isViewImage by remember { mutableStateOf(false) }

    // location check
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    val locationSettingsLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
    }

    LaunchedEffect(key1 = foodAcceptViewModel, block = {
        foodAcceptViewModel.getFoodDetailState(foodId)
        if (getFoodDetails.data == null){
            foodAcceptViewModel.getSwipeToRefresh()
        }
    })

    if (getFoodDetails.isLoading) {
        ProgressIndicatorView()
    }

    if(getFoodAcceptState.isLoading) {
        ProcessingDialogView()
    }

    if (getFoodDetails.error != null || getFoodAcceptState.error != null) {
        DisplayErrorMessageView(
            text = getFoodAcceptState.error ?: getFoodAcceptState.error ?: stringResource(R.string.food_not_found),
            vectorIcon = if (foodAcceptViewModel.isRefreshing) null else Icons.Default.Refresh,
            onClick = { foodAcceptViewModel.getSwipeToRefresh() }
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
            title = title ?: stringResource(id = R.string.food_details),
            color = textColor,
            backgroundColor = white,
            leadingIcon = Icons.Default.ArrowBackIosNew,
            modifier = Modifier
                .shadow(4.dp)
                .fillMaxWidth(),
            onClickLeadingIcon = {
                mainNavController.navigateUp()
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
                            .padding(bottom = 4.dp),
                    ) {
                        ViewFoodDetails(
                            food = it,
                            context = context,
                            isBtnVisible = true,
                            onClickViewImage = {
                                isViewImage = !isViewImage
                            },
                            onClickViewMap = {
                                if(isLocationEnabled(context)) {
                                    mainNavController.navigate("GoogleMapView/${it.latitude.toString()}/${it.longitude.toString()}/${it.username}")
                                } else {
                                    showToast(context, context.getString(R.string.please_enable_location_services))
                                    locationSettingsLauncher.launch(intent)
                                }
                            },
                            onClickViewProfile = {
                                mainNavController.navigate("UserDetailView/${it.userId}")
                            },
                            status = it.status,
                            onClickAcceptBtn = {
                                MainScope().launch {
                                    foodAcceptViewModel.getAcceptFood(foodId, "Pending", userId, username)
                                }
                            },
                            onClickCompletedBtn = {
                                mainNavController.navigate("DonationRating/${foodId}/${it.foodName}/${email}")
                            }
                        )
                    }
                }
            }
        }
    }

    if (isViewImage){
        ImageDialogView(streamUrl = getFoodDetails.data?.streamUrl){
            isViewImage = false
        }
    }

    if (isSuccess){
        SuccessMessageDialogBox(
            title = stringResource(R.string.food_accepted),
            descriptions = getFoodAcceptState.data?.message,
            onDismiss = {
                mainNavController.navigate(NavScreen.VolunteerDashboardPage.route)
                // navController.navigate(BtnNavScreen.Home.route)
                isSuccess = false
            }
        )
    }
}