package com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Report
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
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.ReportDTO
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.utils.compose.ContentCardView
import com.sitaram.foodshare.theme.black
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.red
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.compose.ConfirmationDialogView
import com.sitaram.foodshare.utils.compose.DisplayErrorMessageView
import com.sitaram.foodshare.utils.compose.InputDialogBoxView
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.NormalTextView
import com.sitaram.foodshare.utils.compose.PainterImageView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.PullRefreshIndicatorView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.TopAppBarIconView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterialApi::class)
@Composable
fun HistoryDonorScreen(
    mainNavController: NavHostController,
    donorHistoryViewModel: DonorHistoryViewModel = hiltViewModel(),
) {

    // Check The Internet Connection
    val context = LocalContext.current
    val userInterceptors = UserInterceptors(context)
    val userId = userInterceptors.getUserId()
    val userRole = userInterceptors.getUserRole()
    val username = userInterceptors.getUserName()

    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available
    val getDonorHistory = donorHistoryViewModel.donorHistoryState

    var foodId by remember { mutableIntStateOf(0) }
    var itemIndex by remember { mutableIntStateOf(0) }
    var isDeleteConfirmation by remember { mutableStateOf(false) }
    var isReportConfirmation by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit, block = {
        if (isConnected) {
            donorHistoryViewModel.getDonorHistory(userId)
        }
    })

    val pullRefreshState = rememberPullRefreshState(
        refreshing = donorHistoryViewModel.isRefreshing,
        onRefresh = { donorHistoryViewModel.getSwipeToRefresh() }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
            .background(white),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBarIconView(
            title = stringResource(R.string.donation_history),
            modifier = Modifier.shadow(1.5.dp),
            navigationIcon = { PainterImageView(painter = painterResource(id = R.mipmap.img_app_logo)) },
            backgroundColor = white,
            contentColor = black,
        )

        if (getDonorHistory.isLoading) {
            ProcessingDialogView()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter
        ) {
            if (!isConnected) {
                NetworkIsNotAvailableView()
            } else {
                // Show data if available
                getDonorHistory.data?.data?.let { result ->
                    if (result.isEmpty() && getDonorHistory.data.isSuccess == true) {
                        DisplayErrorMessageView(
                            text = getDonorHistory.error,
                            vectorIcon = if (donorHistoryViewModel.isRefreshing) null else Icons.Default.Refresh,
                            onClick = {
                                donorHistoryViewModel.getSwipeToRefresh()
                                donorHistoryViewModel.clearMessage()
                            }
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 6.dp)
                        ) {
                            itemsIndexed(result) { index, it1 ->
                                ContentCardView(
                                    imageUrl = it1?.foods?.streamUrl,
                                    rating = it1?.histories?.ratingPoint ?: 0,
                                    title = it1?.foods?.foodName,
                                    status = it1?.foods?.status,
                                    donateLocation = it1?.foods?.pickUpLocation,
                                    donationDate = it1?.foods?.createdDate ?: it1?.histories?.distributedDate,
                                    isDonor = userRole.lowercase() == "donor",
                                    onClickReport = {
                                        foodId = it1?.foods?.id ?: 0
                                        isReportConfirmation = true
                                    },
                                    onClickDelete = {
                                        foodId = it1?.foods?.id ?: 0
                                        itemIndex = index
                                        isDeleteConfirmation = true
                                    }
                                ) {
                                    MainScope().launch {
                                        it1?.foods?.let { food ->
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
                                                userId = it1.volunteer?.id ?: it1.donor?.id,
                                                username = it1.volunteer?.username ?: it1.donor?.username,
                                                email = it1.volunteer?.email ?: it1.donor?.email,
                                                contactNumber = it1.volunteer?.contactNumber ?: it1.donor?.contactNumber,
                                                photoUrl = it1.volunteer?.photoUrl ?: it1.donor?.photoUrl,
                                            )
                                        }?.let {
                                            donorHistoryViewModel.saveFoodDetails(foods = it)
                                        }

                                        it1?.histories?.let {
                                            val history = HistoryEntity(
                                                id = it.id,
                                                descriptions = it.descriptions ?: "N/S",
                                                status = it.status ?: "N/S",
                                                distributedLocation = it.distributedLocation ?: "N/S",
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
                                            donorHistoryViewModel.saveHistoryDetails(history)
                                        }
                                    }.job
                                    mainNavController.navigate("FoodDetailView/${it1?.foods?.id}/${it1?.foods?.foodName}/${it1?.histories?.ratingPoint ?: 0}")
                                }
                            }
                        }

                    }
                }

                if (getDonorHistory.error != null) {
                    DisplayErrorMessageView(
                        text = getDonorHistory.error,
                        vectorIcon = if (donorHistoryViewModel.isRefreshing) null else Icons.Default.Refresh,
                        onClick = {
                            donorHistoryViewModel.getSwipeToRefresh()
                            donorHistoryViewModel.clearMessage()
                        }
                    )
                }

                PullRefreshIndicatorView(
                    modifier = Modifier.align(Alignment.TopCenter),
                    refreshing = donorHistoryViewModel.isRefreshing,
                    state = pullRefreshState
                )
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 6.dp))
    }

    LaunchedEffect(getDonorHistory.message){
        if (getDonorHistory.message != null){
            showToast(context, getDonorHistory.message)
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
                    donorHistoryViewModel.getDeleteFood(foodId, username, itemIndex)
                    isDeleteConfirmation = false
                }
            }
        )
    }

    if (isReportConfirmation) {
        var message by remember { mutableStateOf("") }
        InputDialogBoxView(
            title = stringResource(id = R.string.report),
            vectorIcon = Icons.Default.Report,
            tint = red,
            body = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TextView(
                        text = stringResource(R.string.please_provide_your_problem),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    NormalTextView(
                        value = message,
                        onValueChange = { message = it },
                        placeholder = {
                            TextView(
                                text = stringResource(R.string.descriptions),
                                textType = TextType.INPUT_TEXT_VALUE,
                                color = gray
                            )
                        },
                        maxLines = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .height(54.dp)
                    )
                }
            },
            onDismissRequest = { isReportConfirmation = false },
            enabled = message.isNotEmpty(),
            confirmButton = {
                if (isConnected) {
                    val reportDTO = ReportDTO(
                        complaintTo = context.getString(R.string.volunteer),
                        descriptions = message.trim(),
                        createdBy = username,
                        food = foodId
                    )
                    donorHistoryViewModel.getReportToUser(reportDTO)
                } else {
                    showToast(context, context.getString(R.string.no_internet_connection))
                }
                isReportConfirmation = false
            }
        )
    }

    LaunchedEffect(getDonorHistory.message){
        if (getDonorHistory.message != null){
            showToast(context, getDonorHistory.message)
            donorHistoryViewModel.clearMessage()
        }
    }
}