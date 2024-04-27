package com.sitaram.foodshare.features.dashboard.home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.navigations.NavScreen
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.source.local.ProfileEntity
import com.sitaram.foodshare.utils.ApiUrl
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.theme.black
import com.sitaram.foodshare.theme.darkGray
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.ConverterUtil
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.compose.AsyncImageView
import com.sitaram.foodshare.utils.compose.ConfirmationDialogView
import com.sitaram.foodshare.utils.compose.DisplayErrorMessageView
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.PainterImageView
import com.sitaram.foodshare.utils.compose.ProgressIndicatorView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.PullRefreshIndicatorView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.TopAppBarIconView
import com.sitaram.foodshare.utils.compose.VectorIconView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun HomeViewScreen(
    mainNavController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    // Check The Internet Connection
    val context = LocalContext.current
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available

    val getFoodDetails = homeViewModel.homeState
    val getDeleteState = homeViewModel.deleteState
    val interceptors = UserInterceptors(context)
    val userRole = interceptors.getUserRole()
    val userId = interceptors.getUserId()
    val username = interceptors.getUserName()

    var id by remember { mutableIntStateOf(0) }
    var isDeleteConfirmation by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = homeViewModel.isRefreshing,
        onRefresh = { homeViewModel.getSwipeToRefresh() }
    )
    if (getDeleteState.isLoading) {
        ProcessingDialogView()
    }

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
            numOfNotification = getFoodDetails.data?.notification ?: 0,
            navigationIcon = { PainterImageView(painter = painterResource(id = R.mipmap.img_app_logo)) },
            notificationIcon = Icons.Default.NotificationsActive,
        ) {
            mainNavController.navigate(NavScreen.Notification.route)
        }

        if (getFoodDetails.isLoading) {
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
                if (getFoodDetails.error != null || getDeleteState.error != null) {
                    DisplayErrorMessageView(
                        text = getFoodDetails.error ?: getDeleteState.error
                        ?: stringResource(R.string.food_not_found),
                        onClick = { homeViewModel.getSwipeToRefresh() }
                    )
                }

                // Show data if available
                getFoodDetails.data?.foods.let { result ->
                    if (result == null && getFoodDetails.data?.isSuccess == true) {
                        DisplayErrorMessageView(
                            text = getFoodDetails.data.message ?: getDeleteState.data?.message
                            ?: stringResource(R.string.food_not_found),
                            vectorIcon = if (homeViewModel.isRefreshing) null else Icons.Default.Refresh,
                            onClick = { homeViewModel.getSwipeToRefresh() }
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.Top
                        ) {
                            items(result.orEmpty()) { items ->
                                items?.let { food ->
                                    if (food.status?.lowercase() == "new") {
                                        HomeFoodCardView(
                                            streamUrl = food.streamUrl,
                                            foodName = food.foodName,
                                            pickUpLocation = food.pickUpLocation,
                                            descriptions = food.descriptions,
                                            quantity = food.quantity,
                                            isDonor = userRole.lowercase() == "donor" && food.user?.id == userId,
                                            donateDate = food.createdDate,
                                            onClickDelete = {
                                                id = food.id ?: 0
                                                isDeleteConfirmation = true
                                            },
                                            onClick = {
                                                MainScope().launch {
                                                    // result
                                                    food.id?.let {
                                                        FoodsEntity(
                                                            id = it,
                                                            foodName = food.foodName,
                                                            status = food.status,
                                                            foodTypes = food.foodTypes,
                                                            descriptions = food.descriptions,
                                                            streamUrl = food.streamUrl,
                                                            quantity = food.quantity,
                                                            pickUpLocation = food.pickUpLocation,
                                                            expireTime = food.expireTime,
                                                            latitude = food.latitude,
                                                            longitude = food.longitude,
                                                            modifyBy = food.modifyBy,
                                                            createdBy = food.createdBy,
                                                            createdDate = food.createdDate,
                                                            modifyDate = food.modifyDate,
                                                            isDelete = food.isDelete,
                                                            userId = food.user?.id,
                                                            username = food.user?.username,
                                                            email = food.user?.email,
                                                            contactNumber = food.user?.contactNumber,
                                                            photoUrl = food.user?.photoUrl,
                                                        )
                                                    }?.let {
                                                        homeViewModel.saveFoodDetails(foods = it)
                                                    }
                                                    // user profile
                                                    food.user?.id?.let {
                                                        ProfileEntity(
                                                            id = it,
                                                            role = food.user?.role,
                                                            address = food.user?.address
                                                                ?: "N/S",
                                                            isActive = food.user?.isActive,
                                                            gender = food.user?.gender ?: "N/S",
                                                            lastLogin = food.user?.lastLogin
                                                                ?: "N/S",
                                                            dateOfBirth = food.user?.dateOfBirth
                                                                ?: "N/S",
                                                            modifyBy = food.user?.modifyBy
                                                                ?: "N/S",
                                                            createdBy = food.user?.createdBy
                                                                ?: "N/S",
                                                            contactNumber = food.user?.contactNumber
                                                                ?: "N/S",
                                                            isDelete = food.user?.isDelete
                                                                ?: false,
                                                            isAdmin = food.user?.isAdmin,
                                                            aboutsUser = food.user?.aboutsUser
                                                                ?: "N/S",
                                                            photoUrl = food.user?.photoUrl,
                                                            createdDate = food.user?.createdDate
                                                                ?: "N/S",
                                                            modifyDate = food.user?.modifyDate
                                                                ?: "N/S",
                                                            email = food.user?.email ?: "N/S",
                                                            username = food.user?.username
                                                                ?: "Unknow",
                                                            ngo = food.user?.ngo
                                                        )
                                                    }?.let {
                                                        homeViewModel.saveUserProfile(profile = it)
                                                    }
                                                    delay(50)
                                                    // navigate the new page
                                                    if (food.status?.lowercase() == "pending") {
                                                        showToast(
                                                            context,
                                                            context.getString(R.string.fooddetails_already_accepted_thanks_you)
                                                        )
                                                    } else {
                                                        val role =
                                                            UserInterceptors(context).getUserRole()
                                                        if (role.lowercase() == "donor") {
                                                            mainNavController.navigate("FoodDetailView/${food.id}/${food.foodName}/${0}")
                                                        } else {
                                                            mainNavController.navigate("FoodAcceptView/${food.id}/${food.foodName}/${food.user?.email}")
                                                        }
                                                    }
                                                }.job
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        PullRefreshIndicatorView(
                            modifier = Modifier.align(Alignment.TopCenter),
                            refreshing = homeViewModel.isRefreshing,
                            state = pullRefreshState
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(bottom = 6.dp))
            }
        }
    }

    if (isDeleteConfirmation) {
        ConfirmationDialogView(
            title = stringResource(R.string.remove_food),
            descriptions = stringResource(R.string.are_you_sure_you_want_to_remove_this_food),
            confirmBtnText = stringResource(id = R.string.yesDelete),
            onDismiss = { isDeleteConfirmation = false },
            onConfirm = {
                MainScope().launch {
                    homeViewModel.getDeleteFood(id, username)
                    isDeleteConfirmation = false
                }
            }
        )
    }


    LaunchedEffect(key1 = getDeleteState.message, block = {
        if (getDeleteState.data?.isSuccess == true) {
            showToast(context, getDeleteState.message)
            homeViewModel.clearMessage()
        }
    })
}

@Composable
fun HomeFoodCardView(
    streamUrl: String? = null,
    foodName: String? = null,
    descriptions: String? = null,
    pickUpLocation: String? = null,
    quantity: Int? = null,
    isDonor: Boolean = false,
    isActions: Boolean = false,
    donateDate: String? = null,
    onClick: (() -> Unit)? = null,
    onClickDelete: (() -> Unit)? = null,
    onClickReport: (() -> Unit)? = null,
//    onClickCompleted: (() -> Unit)? = null,
) {

    var expandedUpdate by remember { mutableStateOf(false) }
    val getConvertDate = ConverterUtil.convertStringToDate(donateDate)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .shadow(2.dp, shape = ShapeDefaults.Small)
            .background(white)
            .clickable { onClick?.invoke() },
        shape = ShapeDefaults.Small,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(white),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImageView(
                model = streamUrl ?: ApiUrl.FOOD_URL,
                modifier = Modifier
                    .height(110.dp)
                    .width(90.dp)
                    .padding(12.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp)
                    .padding(end = 8.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.End
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .horizontalScroll(rememberScrollState())
                    ) {
                        TextView(
                            text = foodName ?: "",
                            textType = TextType.LARGE_TEXT_SEMI_BOLD,
                            maxLines = 1,
                            color = darkGray,
                            modifier = Modifier
                                .weight(1f)
                        )
                    }

                    if (isDonor) {
                        VectorIconView(
                            imageVector = Icons.Default.DeleteForever,
                            tint = gray,
                            modifier = Modifier
                                .size(25.dp)
                                .clip(CircleShape)
                                .clickable { onClickDelete?.invoke() }
                        )
                    }
                    if (isActions) {
                        VectorIconView(
                            imageVector = Icons.Default.MoreVert,
                            tint = gray,
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .clickable {
                                    expandedUpdate = !expandedUpdate
                                }
                        )

                        // dropdown menu and inside have more item
                        Box(
                            modifier = Modifier
                                .wrapContentSize(Alignment.TopEnd),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            DropdownMenu(
                                expanded = expandedUpdate,
                                onDismissRequest = { expandedUpdate = false },
                                modifier = Modifier.background(colorResource(id = R.color.white))
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        DropdownMenusItems(
                                            text = stringResource(R.string.report),
                                            painter = Icons.Default.Report
                                        )
                                    },
                                    onClick = {
                                        expandedUpdate = false
                                        onClickReport?.invoke()
                                    }
                                )
//                                DropdownMenuItem(
//                                    text = {
//                                        DropdownMenusItems(
//                                            text = stringResource(R.string.completed),
//                                            painter = Icons.Default.Done
//                                        )
//                                    },
//                                    onClick = {
//                                        expandedUpdate = false
//                                        onClickCompleted?.invoke()
//                                    }
//                                )
                            }
                        }
                    }
                }

                TextView(
                    text = pickUpLocation ?: "",
                    maxLines = 2,
                    textType = TextType.BASE_TEXT_REGULAR,
                    color = gray,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextView(
                    text = descriptions ?: "",
                    maxLines = 1,
                    textType = TextType.BASE_TEXT_REGULAR,
                    color = gray,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (quantity != null) {
                        TextView(
                            text = if (quantity > 1) "$quantity Units" else "$quantity Unit",
                            modifier = Modifier.weight(1f),
                            color = gray,
                            textType = TextType.SMALL_TEXT_REGULAR
                        )
                    }
                    if (getConvertDate != null) {
                        TextView(
                            text = getConvertDate,
                            textType = TextType.CAPTION_TEXT,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = gray,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun DropdownMenusItems(text: String, painter: ImageVector) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        VectorIconView(
            imageVector = painter,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(25.dp)
        )
        TextView(text = text, color = colorResource(id = R.color.darkGray))
    }
}