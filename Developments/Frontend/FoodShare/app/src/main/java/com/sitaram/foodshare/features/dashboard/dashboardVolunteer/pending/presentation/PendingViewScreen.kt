package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.ReportDTO
import com.sitaram.foodshare.features.dashboard.home.presentation.HomeFoodCardView
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.HistoryEntity
import com.sitaram.foodshare.source.remote.pojo.ResponsePojo
import com.sitaram.foodshare.theme.black
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.primary
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
import com.sitaram.foodshare.utils.compose.ProgressIndicatorView
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
fun PendingViewScreen(
    navController: NavController,
    pendingViewModel: PendingFoodViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val status by remember { mutableStateOf("Pending") }
    val interceptors = UserInterceptors(context)
    val userId = interceptors.getUserId()
    val username = interceptors.getUserName()
    val getPendingData = pendingViewModel.getHistoryState
    val getReportData = pendingViewModel.getReportState
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available
    var isReportConfirmation by remember { mutableStateOf(false) }
    var isDonationCompleted by remember { mutableStateOf(false) }
    var foodId by remember { mutableIntStateOf(0) }
    var foodName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }

    LaunchedEffect(key1 = Unit, block = {
        if (isConnected) {
            pendingViewModel.getPendingFood(userId, status)
        }
    })

    val pullRefreshState = rememberPullRefreshState(
        refreshing = pendingViewModel.isRefreshing,
        onRefresh = { pendingViewModel.getSwipeToRefresh() }
    )

    if (getPendingData.isLoading) {
        ProgressIndicatorView()
    }

    if (getReportData.isLoading) {
        ProcessingDialogView()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBarIconView(
            title = "Pending Food",
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

                if (getPendingData.error != null || getReportData.error != null) {
                    DisplayErrorMessageView(
                        text = getPendingData.error ?: getReportData.error
                        ?: stringResource(R.string.food_not_found),
                        vectorIcon = if (pendingViewModel.isRefreshing) null else Icons.Default.Refresh,
                        onClick = { pendingViewModel.getSwipeToRefresh() }
                    )
                }

                getPendingData.data?.let { histories ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        items(histories) { it1 ->
                            HomeFoodCardView(
                                streamUrl = it1?.foodDetails?.streamUrl,
                                foodName = it1?.foodDetails?.foodName,
                                pickUpLocation = it1?.foodDetails?.pickUpLocation,
                                descriptions = it1?.foodDetails?.descriptions,
                                quantity = it1?.foodDetails?.quantity,
                                donateDate = it1?.foodDetails?.createdDate,
                                isActions = true,
                                onClickReport = {
                                    foodId = it1?.historyDetails?.food ?: 0
                                    isReportConfirmation = true
                                },
                                onClickCompleted = {
                                    foodId = it1?.historyDetails?.id ?: 0
                                    foodName = it1?.foodDetails?.foodName ?: ""
                                    MainScope().launch {
                                        it1?.historyDetails?.let {
                                            val history = HistoryEntity(
                                                id = it.id,
                                                descriptions = it.descriptions ?: "N/S",
                                                status = "Pending",
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
                                            pendingViewModel.saveHistoryDetails(history)
                                        }
                                    }.job
                                    userEmail = it1?.userDetails?.email ?: ""
                                    isDonationCompleted = true
                                },
                                onClick = {
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
                                            pendingViewModel.saveFoodDetails(foods = it)
                                        }

                                        it1?.historyDetails?.let {
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
                                            pendingViewModel.saveHistoryDetails(history)
                                        }
                                    }.job
                                    navController.navigate("FoodAcceptView/${it1?.foodDetails?.id}/${it1?.foodDetails?.foodName}/${it1?.userDetails?.email}")
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(bottom = 6.dp))
                }
            }
            PullRefreshIndicatorView(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = pendingViewModel.isRefreshing,
                state = pullRefreshState
            )
        }
    }

    if (isDonationCompleted) {
        ConfirmationDialogView(
            title = stringResource(R.string.donation_completed),
            descriptions = stringResource(R.string.are_you_sure_you_want_to_confirm_that_the_food_has_been_distributed_at_this_location),
            onDismiss = { isDonationCompleted = false },
            btnColor = primary,
            onConfirm = {
                navController.navigate("UpdateFoodHistory/${foodId}/${foodName}/${userEmail}")
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
                        complaintTo = context.getString(R.string.donor),
                        descriptions = message.trim(),
                        createdBy = username,
                        food = foodId
                    )
                    pendingViewModel.getReportToUser(reportDTO)
                } else {
                    showToast(context, "No Internet connection")
                }
                isReportConfirmation = false
            }
        )
    }

    LaunchedEffect(key1 = getReportData, block = {
        if (getReportData.data?.isSuccess == true) {
            showToast(context, getReportData.data.message)
            ReportState(data = ResponsePojo(isSuccess = false))
        }
    })
}