package com.sitaram.foodshare.features.dashboard.history.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.sitaram.foodshare.source.local.ProfileEntity
import com.sitaram.foodshare.theme.black
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.compose.ContentCardView
import com.sitaram.foodshare.utils.compose.DisplayErrorMessageView
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.PainterImageView
import com.sitaram.foodshare.utils.compose.ProgressIndicatorView
import com.sitaram.foodshare.utils.compose.PullRefreshIndicatorView
import com.sitaram.foodshare.utils.compose.TopAppBarIconView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterialApi::class)
@Composable
fun HistoryViewScreen(
    mainNavController: NavHostController,
    historyViewModel: HistoryViewModel = hiltViewModel()
) {
    // Check The Internet Connection
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available


    val context = LocalContext.current
    val status by remember { mutableStateOf("Completed") }
    val interceptors = UserInterceptors(context)
    val userId = interceptors.getUserId()

    LaunchedEffect(key1 = Unit, block = {
        historyViewModel.getFoodHistory(userId, status)
    })

    val getHistoryState = historyViewModel.historyState
    val pullRefreshState = rememberPullRefreshState(
        refreshing = historyViewModel.isRefreshing,
        onRefresh = { historyViewModel.getSwipeToRefresh() }
    )

    if (getHistoryState.isLoading) {
        ProgressIndicatorView()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBarIconView(
            title = "Histories",
            modifier = Modifier.shadow(1.5.dp),
            navigationIcon = { PainterImageView(painter = painterResource(id = R.mipmap.img_app_logo)) },
            backgroundColor = white,
            contentColor = black,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter
        ) {
            if (!isConnected) {
                NetworkIsNotAvailableView()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (getHistoryState.error != null) {
                        DisplayErrorMessageView(
                            text = getHistoryState.error,
                            vectorIcon = if (historyViewModel.isRefreshing) null else Icons.Default.Refresh,
                            onClick = { historyViewModel.getSwipeToRefresh() }
                        )
                    }

                    getHistoryState.data?.foods?.let { result ->
                        if (result.isEmpty() && getHistoryState.data.isSuccess == true) {
                            DisplayErrorMessageView(
                                text = getHistoryState.data.message ?: getHistoryState.data.message
                                ?: stringResource(R.string.food_not_found),
                                vectorIcon = if (historyViewModel.isRefreshing) null else Icons.Default.Refresh,
                                onClick = { historyViewModel.getSwipeToRefresh() }
                            )
                        } else {
                            LazyColumn(modifier = Modifier.fillMaxSize()
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 56.dp)
                            ) {
                                items(result) { items ->
                                    items.let { it ->
                                        ContentCardView(
                                            imageUrl = it.food?.streamUrl,
                                            rating = 2, //it.histories?.first()?.history?.ratingPoint,
                                            title = it.food?.foodName,
                                            donateLocation = it.food?.pickUpLocation,
                                            donationDate = "2034-01-20", // it.food?.createdDate ?: it.histories?.first()?.history?.createdDate,
                                            status = it.food?.status,
                                            onClick = {
                                                MainScope().launch {
                                                    // foods
                                                    it.food?.let { food ->
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
                                                            username = it.donor?.username,
                                                            contactNumber = it.donor?.contactNumber,
                                                            photoUrl = it.donor?.photoUrl,
                                                        )
                                                    }?.let {
                                                        historyViewModel.saveFoodDetails(foods = it)
                                                    }
                                                    // user profile
                                                    it.donor?.let { donor ->
                                                        ProfileEntity(
                                                            id = donor.id,
                                                            role = donor.role ?: "N/S",
                                                            address = donor.address ?: "N/S",
                                                            isActive = donor.isActive ?: false,
                                                            gender = donor.gender ?: "N/S",
                                                            lastLogin = donor.lastLogin ?: "N/S",
                                                            dateOfBirth = donor.dateOfBirth
                                                                ?: "N/S",
                                                            modifyBy = donor.modifyBy ?: "N/S",
                                                            createdBy = donor.createdBy ?: "N/S",
                                                            contactNumber = donor.contactNumber
                                                                ?: "N/S",
                                                            isDelete = donor.isDelete ?: false,
                                                            isAdmin = donor.isAdmin ?: false,
                                                            aboutsUser = donor.aboutsUser ?: "N/S",
                                                            photoUrl = donor.photoUrl ?: "N/S",
                                                            createdDate = donor.createdDate
                                                                ?: "N/S",
                                                            modifyDate = donor.modifyDate ?: "N/S",
                                                            email = donor.email ?: "N/S",
                                                            username = donor.username ?: "Unknow"
                                                        )
                                                    }?.let {
                                                        historyViewModel.saveUserProfile(profile = it)
                                                    }
                                                    it.volunteer?.let { volunteer ->
                                                        ProfileEntity(
                                                            id = volunteer.id,
                                                            role = volunteer.role ?: "N/S",
                                                            address = volunteer.address ?: "N/S",
                                                            isActive = volunteer.isActive ?: false,
                                                            gender = volunteer.gender ?: "N/S",
                                                            lastLogin = volunteer.lastLogin
                                                                ?: "N/S",
                                                            dateOfBirth = volunteer.dateOfBirth
                                                                ?: "N/S",
                                                            modifyBy = volunteer.modifyBy ?: "N/S",
                                                            createdBy = volunteer.createdBy
                                                                ?: "N/S",
                                                            contactNumber = volunteer.contactNumber
                                                                ?: "N/S",
                                                            isDelete = volunteer.isDelete ?: false,
                                                            isAdmin = volunteer.isAdmin ?: false,
                                                            aboutsUser = volunteer.aboutsUser
                                                                ?: "N/S",
                                                            photoUrl = volunteer.photoUrl ?: "N/S",
                                                            createdDate = volunteer.createdDate
                                                                ?: "N/S",
                                                            modifyDate = volunteer.modifyDate
                                                                ?: "N/S",
                                                            email = volunteer.email ?: "N/S",
                                                            username = volunteer.username
                                                                ?: "Unknow"
                                                        )
                                                    }?.let {
                                                        historyViewModel.saveUserProfile(profile = it)
                                                    }

                                                    // history
                                                    it.foodHistory?.let { history ->
                                                        HistoryEntity(
                                                            id = history.id,
                                                            modifyBy = history.modifyBy ?: "N/S",
                                                            createdBy = history.createdBy ?: "N/S",
                                                            isDelete = history.isDelete ?: false,
                                                            createdDate = history.createdDate
                                                                ?: "N/S",
                                                            modifyDate = history.modifyDate
                                                                ?: "N/S",
                                                            descriptions = history.descriptions
                                                                ?: "N/S",
                                                            distributedDate = history.distributedDate
                                                                ?: "N/S",
                                                            distributedLocation = history.distributedLocation
                                                                ?: "N/S",
                                                            status = history.status ?: "N/S",
                                                            food = history.food,
                                                            ratingPoint = history.ratingPoint,
                                                            volunteer = history.volunteer,
                                                        )
                                                    }?.let {
                                                        historyViewModel.saveHistory(profile = it)
                                                    }
                                                }.job
                                                // navigate the new page
                                                mainNavController.navigate("FoodDetailView/${it.food?.id}/${it.food?.foodName}")
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            PullRefreshIndicatorView(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = historyViewModel.isRefreshing,
                state = pullRefreshState
            )
        }
    }
}