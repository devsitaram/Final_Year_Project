package com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.theme.black
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.compose.DisplayErrorMessageView
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.PainterImageView
import com.sitaram.foodshare.utils.compose.ProgressIndicatorView
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.TopAppBarIconView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun AdminHomeViewScreen(
    navController: NavHostController,
    mainNavController: NavHostController,
    adminHomeViewModel: AdminHomeViewModel = hiltViewModel(),
) {
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available
    val getHomeState = adminHomeViewModel.adminHomeState

    if (getHomeState.isLoading) {
        ProgressIndicatorView()
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
                notificationIcon = Icons.Default.NotificationsActive,
            ) {
                showSnackBar.invoke()
            }

            if (getHomeState.isLoading) {
                ProgressIndicatorView()
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
                    if (getHomeState.error != null) {
                        DisplayErrorMessageView(
                            text = getHomeState.error,
                            onClick = { adminHomeViewModel.getSwipeToRefresh() }
                        )
                    }

                    // Show data if available
                    getHomeState.data?.data.let { result ->
                        if (result == null && getHomeState.data?.isSuccess == true) {
                            DisplayErrorMessageView(
                                text = getHomeState.data.message,
                                vectorIcon = if (adminHomeViewModel.isRefreshing) null else Icons.Default.Refresh,
                                onClick = { adminHomeViewModel.getSwipeToRefresh() }
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.Top
                            ) {
                                items(result.orEmpty()) { items ->
                                    items?.let { report ->
                                        TextView(text = report.reportDetails?.descriptions ?: "")
                                        TextView(text = report.reportDetails?.complaintTo ?: "")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}