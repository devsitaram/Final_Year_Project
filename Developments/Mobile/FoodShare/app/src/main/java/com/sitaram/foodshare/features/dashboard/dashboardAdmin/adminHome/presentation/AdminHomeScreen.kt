package com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.data.pojo.Data
import com.sitaram.foodshare.theme.backgroundLayoutColor
import com.sitaram.foodshare.theme.black
import com.sitaram.foodshare.theme.blue
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.red
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.ApiUrl
import com.sitaram.foodshare.utils.ConverterUtil.convertStringToDate
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.getEmailSend
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.getPhoneCall
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.compose.AsyncImageView
import com.sitaram.foodshare.utils.compose.CheckboxView
import com.sitaram.foodshare.utils.compose.DisplayErrorMessageView
import com.sitaram.foodshare.utils.compose.DividerView
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.PainterImageView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.ProgressIndicatorView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.TopAppBarIconView
import com.sitaram.foodshare.utils.compose.VectorIconView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun AdminHomeViewScreen(
    navController: NavHostController,
    mainNavController: NavHostController,
    adminHomeViewModel: AdminHomeViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available
    val getHomeState = adminHomeViewModel.adminHomeState

    if (getHomeState.isLoading) {
        ProcessingDialogView()
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = adminHomeViewModel.isRefreshing,
        onRefresh = { adminHomeViewModel.getSwipeToRefresh() }
    )
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val showSnackBar: () -> Unit = {
        coroutineScope.launch {
            snackBarHostState.showSnackbar("You have ${getHomeState.data?.data?.size ?: 0} complaint's notification${if (getHomeState.data?.data?.size == 1) "" else "s"}")
        }
    }

    Scaffold(
        snackbarHost = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                SnackbarHost(hostState = snackBarHostState)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 55.dp)
                .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBarIconView(
                title = stringResource(R.string.home),
                modifier = Modifier.shadow(1.5.dp),
                backgroundColor = white,
                contentColor = black,
                numOfNotification = getHomeState.data?.data?.size ?: 0,
                navigationIcon = { PainterImageView(painter = painterResource(id = R.mipmap.img_app_logo)) },
                notificationIcon = Icons.Default.NotificationsActive
            ) {
                showSnackBar.invoke()
            }

            if (getHomeState.isLoading) {
                ProgressIndicatorView()
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
                    .background(backgroundLayoutColor),
                contentAlignment = Alignment.TopCenter
            ) {

                if (!isConnected) {
                    NetworkIsNotAvailableView()
                } else {
                    // Show data if available
                    getHomeState.data?.data.let { result ->
                        if (result?.isEmpty() == true && getHomeState.data?.isSuccess == true) {
                            DisplayErrorMessageView(
                                text = stringResource(R.string.report_is_not_found),
                                vectorIcon = if (adminHomeViewModel.isRefreshing) null else Icons.Default.Refresh,
                                onClick = { adminHomeViewModel.getSwipeToRefresh() }
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalArrangement = Arrangement.Top
                            ) {
                                itemsIndexed(result.orEmpty()) { index, items ->
                                    items?.let {
                                        ReportCardView(
                                            items = items,
                                            context = context,
                                            onVerify = {
                                                MainScope().launch {
                                                    adminHomeViewModel.getVerifyReport(
                                                        id = items.reportDetails?.id,
                                                        isVerify = it,
                                                        index = index
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (getHomeState.error != null) {
                        DisplayErrorMessageView(
                            text = getHomeState.error,
                            onClick = { adminHomeViewModel.getSwipeToRefresh() }
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(getHomeState.message){
        if (getHomeState.message != null){
            showToast(context, getHomeState.message)
            adminHomeViewModel.clearMessage()
        }
    }
}

@Composable
fun ReportCardView(
    items: Data,
    context: Context,
    onVerify: (Boolean) -> Unit,
) {

    var isViewDetails by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable { isViewDetails = !isViewDetails }
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AsyncImageView(
                        model = if (items.user?.photoUrl != null) items.user?.photoUrl else ApiUrl.PROFILE_URL,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)
                    ) {
                        TextView(
                            text = items.user?.username ?: stringResource(R.string.user),
                            textType = TextType.TITLE4,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                        )

                        TextView(
                            text = items.user?.email ?: stringResource(R.string.email_not_found),
                            textType = TextType.BASE_TEXT_REGULAR,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        TextView(
                            text = items.user?.contactNumber
                                ?: stringResource(R.string.no_contact_number),
                            textType = TextType.BASE_TEXT_REGULAR,
                            modifier = Modifier.padding(top = 2.dp)
                        )

                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    TextView(
                        text = items.user?.role ?: stringResource(id = R.string.role),
                        textType = TextType.BASE_TEXT_REGULAR,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp)
                    )

                    if (items.user?.contactNumber != null) {
                        IconButton(
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(CircleShape)
                                .background(white)
                                .size(30.dp),
                            onClick = {
                                getPhoneCall(items.user?.contactNumber, context)
                            }
                        ) {
                            VectorIconView(
                                imageVector = Icons.Default.Phone,
                                tint = blue,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    } else {
                        IconButton(
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(CircleShape)
                                .background(white)
                                .size(30.dp),
                            onClick = {
                                getEmailSend(items.user?.email, context)
                            }
                        ) {
                            VectorIconView(
                                imageVector = Icons.Default.Email,
                                tint = blue,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                }

                TextView(
                    text = items.user?.aboutsUser ?: stringResource(R.string.user_report),
                    textType = TextType.BASE_TEXT_REGULAR,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 4.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    TextView(
                        text = "Food Name: ${items.food?.foodName}",
                        textType = TextType.BASE_TEXT_SEMI_BOLD,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp)
                    )
                    IconButton(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(CircleShape)
                            .background(white)
                            .size(30.dp),
                        onClick = { isViewDetails = !isViewDetails }
                    ) {
                        VectorIconView(
                            imageVector = if (isViewDetails) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            tint = if (isViewDetails) primary else gray,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                }
            }

            if (isViewDetails) {
                DividerView()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isViewDetails = !isViewDetails },
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        AsyncImageView(
                            model = if (items.food?.streamUrl != null) (ApiUrl.API_BASE_URL + items.food?.streamUrl) else ApiUrl.FOOD_URL,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(vertical = 12.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 8.dp), verticalArrangement = Arrangement.Top
                        ) {
                            convertStringToDate(items.food?.createdDate)?.let {
                                TextView(
                                    text = it,
                                    textType = TextType.BASE_TEXT_REGULAR,
                                    modifier = Modifier
                                )
                            }

                            TextView(
                                text = items.food?.descriptions ?: "",
                                textType = TextType.BASE_TEXT_REGULAR,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                    TextView(
                        text = "Complaint To: ${items.reportDetails?.complaintTo ?: stringResource(R.string.report_for_user)}",
                        textType = TextType.BASE_TEXT_REGULAR,
                        color = red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, start = 4.dp)
                    )
                    TextView(
                        text = "Report: ${items.reportDetails?.descriptions ?: stringResource(R.string.user_report)}",
                        textType = TextType.BASE_TEXT_REGULAR,
                        color = red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, start = 4.dp)
                    )
                    convertStringToDate(items.reportDetails?.createdDate)?.let {
                        TextView(
                            text = it,
                            textType = TextType.CAPTION_TEXT,
                            color = red,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp, start = 4.dp)
                        )
                    }

                    var checkedState by remember { mutableStateOf(false) }
                    CheckboxView(
                        text = stringResource(R.string.verify_the_complaint_report),
                        checked = checkedState,
                        onCheckedChange = {
                            checkedState = it
                            onVerify.invoke(it)
                        },
                        leftSize = 1,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}