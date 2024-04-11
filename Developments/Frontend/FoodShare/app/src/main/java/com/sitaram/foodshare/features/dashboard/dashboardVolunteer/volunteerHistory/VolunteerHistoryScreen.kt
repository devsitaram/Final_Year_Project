package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.volunteerHistory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.theme.black
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.compose.ConfirmationDialogView
import com.sitaram.foodshare.utils.compose.ContentCardView
import com.sitaram.foodshare.utils.compose.DisplayErrorMessageView
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.PainterImageView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.ProgressIndicatorView
import com.sitaram.foodshare.utils.compose.PullRefreshIndicatorView
import com.sitaram.foodshare.utils.compose.TopAppBarIconView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterialApi::class)
@Composable
fun VolunteerHistoryViewScreen(
    mainNavController: NavHostController,
    historyViewModel: VolunteerHistoryViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val status by remember { mutableStateOf("Completed") }
    val interceptors = UserInterceptors(context)
    val userId = interceptors.getUserId()
    val username = interceptors.getUserName()
    val userRole = interceptors.getUserRole()

    val getHistoryState = historyViewModel.getHistoryState
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available

    var historyId by remember { mutableIntStateOf(0) }
    var itemIndex by remember { mutableIntStateOf(0) }
    var isDeleteConfirmation by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit, block = {
        if (isConnected) {
            historyViewModel.getPendingFood(userId, status)
        }
    })

    val pullRefreshState = rememberPullRefreshState(
        refreshing = historyViewModel.isRefreshing,
        onRefresh = { historyViewModel.getSwipeToRefresh() }
    )

    if (getHistoryState.isLoading) {
        ProcessingDialogView()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBarIconView(
            title = "History",
            modifier = Modifier.shadow(1.5.dp),
            navigationIcon = { PainterImageView(painter = painterResource(id = R.mipmap.img_app_logo)) },
            backgroundColor = white,
            contentColor = black,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.Center
        ) {
            if (!isConnected) {
                NetworkIsNotAvailableView()
            } else {

                if (getHistoryState.error != null) {
                    DisplayErrorMessageView(
                        text = getHistoryState.error,
                        vectorIcon = if (historyViewModel.isRefreshing) null else Icons.Default.Refresh,
                        onClick = { historyViewModel.getSwipeToRefresh() }
                    )
                }

                getHistoryState.data?.let { histories ->
                    LazyColumn(modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 56.dp)) {
                        itemsIndexed(histories) {index, it1 ->
                            ContentCardView(
                                imageUrl = it1?.foodDetails?.streamUrl,
                                rating = it1?.historyDetails?.ratingPoint ?: 0,
                                title = it1?.foodDetails?.foodName ?: "",
                                status = it1?.historyDetails?.status ?: "",
                                donateLocation = it1?.historyDetails?.distributedLocation ?: "",
                                donationDate =  it1?.historyDetails?.createdDate ?: it1?.foodDetails?.createdDate,
                                isVolunteer = userRole.lowercase() == "volunteer",
                                onClickDelete = {
                                    historyId = it1?.historyDetails?.id ?: 0
                                    itemIndex = index
                                    isDeleteConfirmation = true
                                }
                            ){
                                MainScope().launch {
                                    it1?.foodDetails?.let { food ->
                                        FoodsEntity(
                                            id = food.id,
                                            foodName = food.foodName,
                                            status = food.status,
                                            foodTypes = food.foodTypes,
                                            descriptions = food.descriptions,
                                            streamUrl = food.streamUrl,
                                            quantity = food.quantity,
                                            pickUpLocation = food.pickUpLocation,
                                            expireTime = food.expireTime,
                                            latitude = food.latitude?.toDouble(),
                                            longitude = food.longitude?.toDouble(),
                                            modifyBy = food.modifyBy,
                                            createdBy = food.createdBy,
                                            createdDate = food.createdDate,
                                            modifyDate = food.modifyDate,
                                            isDelete = food.isDelete,
                                            userId = food.donor,
                                            username = it1.userDetails?.username,
                                            contactNumber = it1.userDetails?.contactNumber,
                                            photoUrl = it1.userDetails?.photoUrl,
                                        )
                                    }?.let {
                                        historyViewModel.saveFoodDetails(foods = it)
                                    }

                                    it1?.historyDetails?.let {
                                        val history = HistoryEntity(
                                            id = it.id,
                                            descriptions = it.descriptions ?: "N/S",
                                            status = it.status ?: "N/S",
                                            distributedLocation = it.distributedLocation
                                                ?: "N/S",
                                            distributedDate = it.distributedDate ?: "N/S",
                                            ratingPoint = it.ratingPoint ?: 0,
                                            food = it.food,
                                            volunteer = it.volunteer,
                                            createdBy = it.createdBy ?: "N/S",
                                            createdDate = it.createdDate ?: "N/S",
                                            modifyBy = it.modifyBy ?: "N/S",
                                            modifyDate = it.modifyDate ?: "N/S",
                                            isDelete = it.isDelete ?: false
                                        )
                                        historyViewModel.saveHistoryDetails(history)
                                    }
                                }.job
                                mainNavController.navigate("FoodDetailView/${it1?.foodDetails?.id}/${it1?.foodDetails?.foodName}/${it1?.historyDetails?.ratingPoint}")
                            }
                        }
                    }
                    PullRefreshIndicatorView(
                        modifier = Modifier.align(Alignment.TopCenter),
                        refreshing = historyViewModel.isRefreshing,
                        state = pullRefreshState
                    )
                    Spacer(modifier = Modifier.padding(bottom = 6.dp))
                }
            }

        }
    }

    if (isDeleteConfirmation) {
        ConfirmationDialogView(
            title = stringResource(R.string.remove_history),
            descriptions = stringResource(R.string.are_you_sure_you_want_to_remove_this_food),
            confirmBtnText = stringResource(id = R.string.yesDelete),
            onDismiss = { isDeleteConfirmation = false },
            onConfirm = {
                MainScope().launch {
                    historyViewModel.getDeleteHistory(historyId, username, itemIndex, context)
                    isDeleteConfirmation = false
                }
            }
        )
    }

    LaunchedEffect(getHistoryState.message){
        if (getHistoryState.message != null){
            showToast(context, getHistoryState.message)
            historyViewModel.clearMessage()
        }
    }
}