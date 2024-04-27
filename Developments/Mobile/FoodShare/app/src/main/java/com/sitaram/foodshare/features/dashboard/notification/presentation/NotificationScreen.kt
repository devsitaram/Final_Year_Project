package com.sitaram.foodshare.features.dashboard.notification.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.notification.data.Notification
import com.sitaram.foodshare.source.local.NotificationEntity
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.ConverterUtil.convertStringToDate
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.isCurrentDate
import com.sitaram.foodshare.utils.compose.ChipView
import com.sitaram.foodshare.utils.compose.DisplayErrorMessageView
import com.sitaram.foodshare.utils.compose.DividerView
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.PullRefreshIndicatorView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.TopAppBarView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun NotificationViewScreen(
    mainNavController: NavHostController,
    notificationViewModel: NotificationViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        notificationViewModel.getNotification()
        notificationViewModel.getViewNotification()
    }

    // Check The Internet Connection
    val context = LocalContext.current
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available
    val pullRefreshState = rememberPullRefreshState(
        refreshing = notificationViewModel.isRefreshing,
        onRefresh = { notificationViewModel.getSwipeToRefresh() }
    )

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    var descriptions by remember {
        mutableStateOf("")
    }
    val showSnackBar: () -> Unit = {
        coroutineScope.launch {
            snackBarHostState.showSnackbar(descriptions)
        }
    }

    val getNotification = notificationViewModel.notificationState
    val getNotificationLocalState = notificationViewModel.notificationLocalState

    if (getNotification.isLoading || getNotificationLocalState.isLoading) {
        ProcessingDialogView()
    }

    if (getNotification.error != null || getNotificationLocalState.error != null) {
        DisplayErrorMessageView(
            text = getNotification.error ?: getNotificationLocalState.error,
            vectorIcon = if (notificationViewModel.isRefreshing) null else Icons.Default.Refresh,
            onClick = { notificationViewModel.getSwipeToRefresh() }
        )
    }

    Scaffold(
        snackbarHost = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp), contentAlignment = Alignment.BottomCenter
            ) {
                SnackbarHost(hostState = snackBarHostState)
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBarView(
                title = stringResource(R.string.notifications),
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
            DividerView()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState),
                contentAlignment = Alignment.TopCenter
            ) {
                if (!isConnected) {
                    NetworkIsNotAvailableView()
                } else {
                    getNotification.data?.notifications?.let { notifications ->
                        getNotificationLocalState.data?.let { localNotifications ->
                            val notificationMap = localNotifications.associateBy { it.id }
                            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                items(notifications) { remoteNotification ->
                                    val isCurrentDate =
                                        isCurrentDate("${remoteNotification?.createdDate}")
                                    val localNotification = notificationMap[remoteNotification?.id]
                                    NotificationItemView(
                                        remoteNotification = remoteNotification,
                                        localNotification= localNotification,
                                        isCurrentDate = isCurrentDate,
                                    ) {
                                        MainScope().launch {
                                            val notification = NotificationEntity(
                                                id = remoteNotification?.id,
                                                createdBy = remoteNotification?.createdBy,
                                                createdDate = remoteNotification?.createdDate,
                                                descriptions = remoteNotification?.descriptions,
                                                isDelete = remoteNotification?.isDelete,
                                                food = remoteNotification?.food,
                                                title = remoteNotification?.title
                                            )
                                            if (localNotification?.food != remoteNotification?.food) {
                                                notificationViewModel.setViewNotification(
                                                    notification
                                                )
                                            }
                                        }
                                        descriptions =
                                            remoteNotification?.descriptions ?: ""
                                        showSnackBar.invoke()
                                    }
//                                    Column(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .background(white)
//                                            .clickable {
//                                                MainScope().launch {
//                                                    val notification = NotificationEntity(
//                                                        id = remoteNotification?.id,
//                                                        createdBy = remoteNotification?.createdBy,
//                                                        createdDate = remoteNotification?.createdDate,
//                                                        descriptions = remoteNotification?.descriptions,
//                                                        isDelete = remoteNotification?.isDelete,
//                                                        food = remoteNotification?.food,
//                                                        title = remoteNotification?.title
//                                                    )
//                                                    if (localNotification?.food != remoteNotification?.food) {
//                                                        notificationViewModel.setViewNotification(
//                                                            notification
//                                                        )
//                                                    }
//                                                }
//                                                descriptions =
//                                                    remoteNotification?.descriptions ?: ""
//                                                showSnackBar.invoke()
//                                            }
//                                    ) {
//                                        Row(
//                                            modifier = Modifier
//                                                .padding(horizontal = 16.dp)
//                                                .padding(top = 8.dp),
//                                            verticalAlignment = Alignment.CenterVertically
//                                        ) {
//                                            TextView(
//                                                text = "${remoteNotification?.title}",
//                                                textType = TextType.LARGE_TEXT_SEMI_BOLD,
//                                                maxLines = 2,
//                                                overflow = TextOverflow.Ellipsis,
//                                                modifier = Modifier.weight(1f)
//                                            )
//                                            if (isCurrentDate && localNotification?.food != remoteNotification?.food) {
//                                                ChipView(
//                                                    modifier = Modifier.size(10.dp),
//                                                    colors = ChipDefaults.chipColors(primary),
//                                                )
//                                                TextView(
//                                                    text = stringResource(id = R.string.new_),
//                                                    textType = TextType.CAPTION_TEXT,
//                                                    modifier = Modifier.padding(start = 4.dp)
//                                                )
//                                            }
//                                        }
//                                        TextView(
//                                            text = remoteNotification?.descriptions ?: "",
//                                            maxLines = 2,
//                                            overflow = TextOverflow.Ellipsis,
//                                            textType = TextType.BASE_TEXT_REGULAR,
//                                            color = textColor,
//                                            modifier = Modifier.padding(
//                                                vertical = 6.dp,
//                                                horizontal = 16.dp
//                                            )
//                                        )
//                                        Row(
//                                            modifier = Modifier
//                                                .padding(horizontal = 16.dp)
//                                                .padding(bottom = 8.dp),
//                                            verticalAlignment = Alignment.CenterVertically
//                                        ) {
//                                            TextView(
//                                                text = "${remoteNotification?.createdBy}",
//                                                textType = TextType.SMALL_TEXT_REGULAR,
//                                                color = textColor,
//                                                modifier = Modifier.weight(1f)
//                                            )
//                                            convertStringToDate(
//                                                remoteNotification?.createdDate ?: ""
//                                            )?.let { it1 ->
//                                                TextView(
//                                                    text = it1,
//                                                    textType = TextType.CAPTION_TEXT,
//                                                    color = gray
//                                                )
//                                            }
//                                        }
//                                    }
//                                    DividerView(modifier = Modifier.padding(horizontal = 8.dp))
                                }
                            }
                        }
                        PullRefreshIndicatorView(
                            modifier = Modifier.align(Alignment.TopCenter),
                            refreshing = notificationViewModel.isRefreshing,
                            state = pullRefreshState
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationItemView(
    remoteNotification: Notification?,
    localNotification: NotificationEntity? = null,
    isCurrentDate: Boolean,
    onClickAction: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .clickable {
                onClickAction.invoke()
            }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextView(
                text = "${remoteNotification?.title}",
                textType = TextType.LARGE_TEXT_SEMI_BOLD,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            if (isCurrentDate && localNotification?.food != remoteNotification?.food) {
                ChipView(
                    modifier = Modifier.size(10.dp),
                    colors = ChipDefaults.chipColors(primary),
                )
                TextView(
                    text = stringResource(id = R.string.new_),
                    textType = TextType.CAPTION_TEXT,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        TextView(
            text = remoteNotification?.descriptions ?: "",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textType = TextType.BASE_TEXT_REGULAR,
            color = textColor,
            modifier = Modifier.padding(
                vertical = 6.dp,
                horizontal = 16.dp
            )
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextView(
                text = "${remoteNotification?.createdBy}",
                textType = TextType.SMALL_TEXT_REGULAR,
                color = textColor,
                modifier = Modifier.weight(1f)
            )
            convertStringToDate(
                remoteNotification?.createdDate ?: ""
            )?.let { it1 ->
                TextView(
                    text = it1,
                    textType = TextType.CAPTION_TEXT,
                    color = gray
                )
            }
        }
    }
    DividerView(modifier = Modifier.padding(horizontal = 8.dp))
}