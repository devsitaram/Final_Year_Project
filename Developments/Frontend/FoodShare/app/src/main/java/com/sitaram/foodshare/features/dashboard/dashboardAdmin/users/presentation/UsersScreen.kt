package com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
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
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data.Users
import com.sitaram.foodshare.utils.ApiUrl
import com.sitaram.foodshare.source.local.ProfileEntity
import com.sitaram.foodshare.theme.black
import com.sitaram.foodshare.theme.blue
import com.sitaram.foodshare.theme.brown
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.green
import com.sitaram.foodshare.theme.lightGray
import com.sitaram.foodshare.theme.pink
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.red
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.presentation.state.DragDropListState
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil
import com.sitaram.foodshare.utils.compose.AsyncImageView
import com.sitaram.foodshare.utils.compose.ChipView
import com.sitaram.foodshare.utils.compose.DisplayErrorMessageView
import com.sitaram.foodshare.utils.compose.InputTextFieldView
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.PainterImageView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.SwitchView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.TopAppBarIconView
import com.sitaram.foodshare.utils.compose.VectorIconView
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.presentation.state.move
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@SuppressLint("SuspiciousIndentation", "UnnecessaryComposedModifier")
@Composable
fun UsersViewScreenNgo(
    mainNavController: NavHostController,
    usersViewModel: UsersViewModel = hiltViewModel(),
) {

    // Check The Internet Connection
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available

    val context = LocalContext.current
    val getAllUsers = usersViewModel.usersState

    var searchFieldVisibility by remember { mutableStateOf(false) }
    val onClickSearch: () -> Unit = {
        searchFieldVisibility = !searchFieldVisibility
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = usersViewModel.isRefreshing,
        onRefresh = { usersViewModel.getSwipeToRefresh() }
    )

    if (getAllUsers.isLoading) {
        ProcessingDialogView()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBarIconView(
            title = stringResource(R.string.users),
            modifier = Modifier.shadow(1.5.dp),
            navigationIcon = { PainterImageView(painter = painterResource(id = R.mipmap.img_app_logo)) },
            backgroundColor = white,
            contentColor = black,
            vectorIcon = Icons.Default.Search,
            onClickAction = { onClickSearch.invoke() },
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (!isConnected) {
                    NetworkIsNotAvailableView()
                } else {

                    if (getAllUsers.error != null) {
                        DisplayErrorMessageView(
                            text = getAllUsers.error,
                            vectorIcon = if (usersViewModel.isRefreshing) null else Icons.Default.Refresh,
                            onClick = { usersViewModel.getSwipeToRefresh() }
                        )
                    }

                    getAllUsers.data?.users?.let { allUsers ->
                        var filteredUsersToShow by remember { mutableStateOf<List<Users?>?>(null) }
                        val updatedList = filteredUsersToShow?.toMutableList() ?: allUsers.toMutableStateList()
                        val overScrollJob by remember { mutableStateOf<Job?>(null) }
                        val lazyListState = rememberLazyListState()
                        val dragDropListState = remember {
                            DragDropListState(
                                lazyListState = lazyListState,
                                onMove = { fromIndex, toIndex ->
                                    if (fromIndex in updatedList.indices && toIndex in updatedList.indices) {
                                        updatedList.move(fromIndex, toIndex)
                                    }
                                }
                            )
                        }

                        var query by remember { mutableStateOf<String?>(null) }
                        if (searchFieldVisibility) {
                            InputTextFieldView(
                                value = query,
                                onValueChange = { searchText ->
                                    query = searchText
                                    filteredUsersToShow = if (query == null) {
                                        null
                                    } else {
                                        allUsers.filter {
                                            it?.username?.contains(
                                                searchText,
                                                ignoreCase = true
                                            ) == true
                                        }
                                    }
                                },
                                label = stringResource(R.string.search_by_username),
                                placeholder = stringResource(R.string.search),
                                isEmptyValue = false,
                                errorColor = red,
                                leadingIcon = {
                                    VectorIconView(
                                        imageVector = Icons.Default.Search,
                                        modifier = Modifier.size(25.dp),
                                        tint = gray
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp, start = 16.dp, end = 8.dp)
                                    .height(56.dp)
                            )
                        }

                        if (updatedList.isEmpty()) {
                            DisplayErrorMessageView(
                                text = getAllUsers.error ?: getAllUsers.error
                                ?: stringResource(R.string.food_not_found),
                                vectorIcon = if (usersViewModel.isRefreshing) null else Icons.Default.Refresh,
                                onClick = {
                                    query = null
                                    usersViewModel.getSwipeToRefresh()
                                }
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .pointerInput(Unit) {
                                        detectDragGesturesAfterLongPress(
                                            onDrag = { change, offset ->
                                                change.consume()
                                                dragDropListState.onDrag(offset = offset)

                                                if (overScrollJob?.isActive == true)
                                                    return@detectDragGesturesAfterLongPress

                                            },
                                            onDragStart = { offset ->
                                                dragDropListState.onDragStart(offset)
                                            },
                                            onDragEnd = {
                                                usersViewModel.getUpdateState(updatedList)
                                                dragDropListState.onDragInterrupted()
                                            },
                                            onDragCancel = {
                                                dragDropListState.onDragInterrupted()
                                            }
                                        )
                                    }
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp).padding(bottom = 4.dp),
                                state = dragDropListState.lazyListState
                            ) {
                                itemsIndexed(updatedList) { index, item ->
                                    var checked by remember { mutableStateOf(item?.isActive) }
                                    Column(
                                        modifier = Modifier
                                            .composed {
                                                val offsetOrNull =
                                                    dragDropListState.elementDisplacement.takeIf {
                                                        index == dragDropListState.currentIndexOfDraggedItem
                                                    }
                                                Modifier.graphicsLayer(
                                                    translationY = offsetOrNull ?: 0f,
                                                    shadowElevation = offsetOrNull ?: 0f,
                                                    ambientShadowColor = lightGray
                                                )
                                            }
                                            .background(
                                                if (index == dragDropListState.currentIndexOfDraggedItem) lightGray else white,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    ) {
                                        if (item?.role?.lowercase() != "admin") {
                                            UserCardView(
                                                imageUrl = item?.photoUrl,
                                                username = item?.username.orEmpty(),
                                                address = item?.address ?: "",
                                                role = item?.role.orEmpty(),
                                                isDelete = item?.isDelete ?: false,
                                                isActive = item?.isActive ?: false,
                                                context = context,
                                                onClick = {
                                                    MainScope().launch {
                                                        usersViewModel.saveUserProfile(
                                                            profile = ProfileEntity(
                                                                id = item?.id ?: 0,
                                                                role = item?.role ?: "N/S",
                                                                address = item?.address ?: "N/S",
                                                                isActive = item?.isActive,
                                                                gender = item?.gender ?: "N/S",
                                                                lastLogin = item?.lastLogin
                                                                    ?: "N/S",
                                                                dateOfBirth = item?.dateOfBirth
                                                                    ?: "N/S",
                                                                modifyBy = item?.modifyBy ?: "N/S",
                                                                createdBy = item?.createdBy
                                                                    ?: "N/S",
                                                                contactNumber = item?.contactNumber
                                                                    ?: "N/S",
                                                                isDelete = item?.isDelete,
                                                                isAdmin = item?.isAdmin,
                                                                aboutsUser = item?.aboutsUser
                                                                    ?: "N/S",
                                                                photoUrl = item?.photoUrl ?: "N/S",
                                                                createdDate = item?.createdDate
                                                                    ?: "N/S",
                                                                modifyDate = item?.modifyDate
                                                                    ?: "N/S",
                                                                email = item?.email ?: "N/S",
                                                                username = item?.username
                                                                    ?: "Unknown"
                                                            )
                                                        )
                                                    }.job
                                                    mainNavController.navigate("UserDetailView/${item?.id}")
                                                },
                                                checked = checked,
                                                onCheckedChange = {
                                                    checked = it
                                                    MainScope().launch {
                                                        usersViewModel.setAccountVerify(
                                                            userId = item?.id,
                                                            isVerify = !item?.isActive!!
                                                        )
                                                    }.job
                                                }
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.padding(top = 2.dp))
                                }
                            }
                        }
                    }
                }
            }
            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = usersViewModel.isRefreshing,
                state = pullRefreshState,
                contentColor = colorResource(id = R.color.primary)
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserCardView(
    imageUrl: String? = null,
    username: String,
    address: String?,
    role: String? = null,
    isDelete: Boolean,
    isActive: Boolean,
    context: Context,
    onClick: (() -> Unit)? = null,
    checked: Boolean?,
    onCheckedChange: (Boolean) -> Unit,
) {
    val chipColor = getChipColorByRole(role?.lowercase()).copy(alpha = 0.70f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .shadow(2.dp, shape = ShapeDefaults.Small)
            .background(white)
            .clickable { onClick?.invoke() },
        colors = CardDefaults.cardColors(white)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImageView(
                model = if (!imageUrl.isNullOrEmpty()) (ApiUrl.API_BASE_URL + imageUrl) else ApiUrl.PROFILE_URL,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )

            // Use weight and fillMaxHeight to make the Column take up remaining space
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(horizontal = 4.dp, vertical = 4.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextView(
                            text = username,
                            textType = TextType.LARGE_TEXT_SEMI_BOLD,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = textColor,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        TextView(
                            text = address ?: "Unknow",
                            textType = TextType.BASE_TEXT_REGULAR,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = textColor,
                            modifier = Modifier
                        )
                    }

                    VectorIconView(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        tint = gray,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        ChipView(
                            onClick = {
                                val message = when {
                                    isDelete -> "This account is deleted."
                                    isActive -> "This account is activated"
                                    else -> "New account is not verify."
                                }
                                UserInterfaceUtil.showToast(context, message)
                            },
                            text = role ?: "",
                            modifier = Modifier
                                .height(22.dp),
                            colors = ChipDefaults.chipColors(chipColor)
                        )
                    }

                    // Moved the SwitchView to the end
                    if (!isDelete) {
                        SwitchView(
                            checked = checked ?: false,
                            onCheckedChange = { onCheckedChange.invoke(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = green, // Color when switch is checked
                                checkedBorderColor = green,
                                checkedTrackColor = white,
                                uncheckedThumbColor = gray, // Default color when switch is unchecked
                                uncheckedBorderColor = gray,
                                uncheckedTrackColor = white
                            ),
                            modifier = Modifier
                                .scale(0.7f)
                        )
                    } else {
                        VectorIconView(
                            imageVector = Icons.Default.RemoveCircleOutline,
                            tint = red,
                            modifier = Modifier
                                .size(35.dp)
                                .padding(end = 8.dp)
                        )
                    }
                }
            }
        }
    }
}


private fun getChipColorByRole(role: String?): Color {
    return when (role) {
        "donor" -> brown
        "volunteer" -> blue
        "admin" -> primary
        else -> pink
    }
}