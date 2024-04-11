package com.sitaram.foodshare.features.dashboard.userProfileDetails.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.profile.presentation.UserProfileData
import com.sitaram.foodshare.utils.ApiUrl
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.theme.black
import com.sitaram.foodshare.theme.blue
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.green
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.red
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.getEmailSend
import com.sitaram.foodshare.utils.compose.AsyncImageView
import com.sitaram.foodshare.utils.compose.DisplayErrorMessageView
import com.sitaram.foodshare.utils.compose.DividerView
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.ProgressIndicatorView
import com.sitaram.foodshare.utils.compose.PullRefreshIndicatorView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.TopAppBarView
import com.sitaram.foodshare.utils.compose.UserProfileContactView
import com.sitaram.foodshare.utils.compose.VectorIconView
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterialApi::class)
@Composable
fun UserDetailsViewScreen(
    id: Int,
    navController: NavHostController,
    userDetailViewModel: UserDetailViewModel = hiltViewModel(),
) {
    // Check The Internet Connection
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available

    LaunchedEffect(key1 = id, block = {
        if (isConnected) {
            userDetailViewModel.getUserProfileById(id)
        }
    })

    val context = LocalContext.current
    val authUser = UserInterceptors(context).getAuthenticate()
    val getProfileDetails = userDetailViewModel.userDetailState

    val pullRefreshState = rememberPullRefreshState(
        refreshing = userDetailViewModel.isRefreshing,
        onRefresh = { userDetailViewModel.getSwipeToRefresh() }
    )

    if (getProfileDetails.isLoading) {
        ProgressIndicatorView()
    }

    if (getProfileDetails.error != null) {
        DisplayErrorMessageView(
            text = getProfileDetails.error,
            vectorIcon = Icons.Default.Refresh,
            onClick = { userDetailViewModel.getSwipeToRefresh() }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    TopAppBarView(
                        title = stringResource(R.string.profile_details),
                        leadingIcon = Icons.Default.ArrowBackIosNew,
                        color = textColor,
                        backgroundColor = white,
                        modifier = Modifier
                            .shadow(4.dp)
                            .fillMaxWidth(),
                        onClickLeadingIcon = { navController.navigateUp() }
                    )
                    DividerView(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)

                    getProfileDetails.data?.let { profile ->
                        val userName = profile.username ?: "N/S"
                        val userId = profile.id
                        val role = profile.role ?: "N/S"
                        val userEmail = profile.email
                        val aboutsUser =
                            profile.aboutsUser ?: "Please add more details about yourself to your profile."
                        val dateOfBirth = profile.dateOfBirth ?: "N/S"
                        val gender = profile.gender ?: "N/S"
                        val address = profile.address ?: "N/S"
                        val contactNumber = profile.contactNumber ?: "N/S"
                        val profileUrl = profile.photoUrl
                        val isAdmin = profile.isAdmin ?: "N/S"
                        val isActivate = profile.isActive ?: "N/S"
                        val isDelete = profile.isDelete ?: "N/S"
                        val lastLogin = profile.lastLogin ?: "N/S"
                        val createdBy = profile.createdBy ?: "N/S"
                        val createdDate = profile.createdDate ?: "N/Ss"
                        val modifyBy = profile.modifyBy ?: "N/S"
                        val modifyDate = profile.modifyDate ?: "N/S"

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                                .background(white),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImageView(
                                    model = if (profileUrl != null) (ApiUrl.API_BASE_URL + profileUrl) else ApiUrl.PROFILE_URL,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(CircleShape)
                                )
                                TextView(
                                    text = userName,
                                    textType = TextType.LARGE_TEXT_SEMI_BOLD,
                                    color = textColor,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp)
                                )
                                TextView(
                                    text = "Profile Id. $userId",
                                    textType = TextType.LARGE_TEXT_SEMI_BOLD,
                                    color = textColor,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                )
                            }

                            // Profile Info
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalArrangement = Arrangement.Top,
                            ) {
                                TextView(
                                    text = stringResource(R.string.profile_information),
                                    textType = TextType.LARGE_TEXT_BOLD,
                                    color = black,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .padding(vertical = 4.dp, horizontal = 16.dp)
                                        .fillMaxWidth()
                                )
                                DividerView(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    verticalArrangement = Arrangement.Top,
                                ) {
                                    UserProfileData(
                                        icon = Icons.Default.VerifiedUser,
                                        title = "Role",
                                        description = role
                                    )
                                    UserProfileData(
                                        icon = Icons.Default.LocationOn,
                                        title = "Address",
                                        description = address
                                    )
                                    UserProfileData(
                                        icon = Icons.Default.Person,
                                        title = "Gender",
                                        description = gender
                                    )
                                    UserProfileData(
                                        icon = Icons.Default.Cake,
                                        title = "Date Of Birth",
                                        description = dateOfBirth
                                    )
                                }
                            }

                            // Contact Details
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalArrangement = Arrangement.Top,
                            ) {
                                TextView(
                                    text = "Contact Us",
                                    textType = TextType.LARGE_TEXT_BOLD,
                                    color = black,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .padding(vertical = 4.dp, horizontal = 16.dp)
                                        .fillMaxWidth()
                                )
                                DividerView(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    verticalArrangement = Arrangement.Top,
                                ) {
                                    UserProfileContactView(
                                        trailingIcon = Icons.Default.Email,
                                        title = stringResource(id = R.string.email),
                                        description = userEmail,
                                        leadingIcon = Icons.Default.MarkEmailUnread,
                                        tint = blue,
                                        onClickCall = {
                                            if (userEmail?.isNotEmpty() == true && userEmail != "N/S") {
                                                getEmailSend(userEmail, context)
                                            }
                                        }
                                    )
                                    UserProfileContactView(
                                        trailingIcon = Icons.Default.PhoneInTalk,
                                        title = stringResource(id = R.string.contact_number),
                                        description = contactNumber,
                                        leadingIcon = Icons.Default.Call,
                                        onClickCall = {
                                            if (contactNumber.isNotEmpty() && contactNumber != "N/S") {
                                                UserInterfaceUtil.getPhoneCall(
                                                    contactNumber,
                                                    context
                                                )
                                            }
                                        }
                                    )
                                }
                            }

                            // Status Info
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalArrangement = Arrangement.Top,
                            ) {
                                TextView(
                                    text = stringResource(R.string.account_status),
                                    textType = TextType.LARGE_TEXT_BOLD,
                                    color = black,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .padding(vertical = 4.dp, horizontal = 16.dp)
                                        .fillMaxWidth()
                                )
                                DividerView(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    verticalArrangement = Arrangement.Top,
                                ) {
                                    UserProfileData(
                                        icon = Icons.Default.SupervisedUserCircle,
                                        title = stringResource(R.string.admin),
                                        description = "$isAdmin"
                                    )

                                    CustomAccountVerify(
                                        leadingIcon = Icons.Default.DeleteForever,
                                        title = stringResource(R.string.active),
                                        isStatus = "$isActivate",
                                        text = when (isActivate) {
                                            true -> "Verified"
                                            else -> "Unverified"
                                        },
                                        color = when (isActivate) {
                                            true -> green
                                            else -> gray
                                        }
                                    )

                                    CustomAccountVerify(
                                        leadingIcon = Icons.Default.DeleteForever,
                                        title = stringResource(id = R.string.deactivate),
                                        isStatus = "$isDelete",
                                        text = when (isDelete) {
                                            true -> "Deactivate"
                                            else -> "Activate"
                                        },
                                        color = when (isDelete) {
                                            true -> red
                                            else -> gray
                                        }
                                    )
                                }
                            }

                            // Additional Information
                            if (authUser?.role == "admin") {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalArrangement = Arrangement.Top,
                                ) {
                                    TextView(
                                        text = stringResource(R.string.additional_activities),
                                        textType = TextType.LARGE_TEXT_BOLD,
                                        color = black,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .padding(vertical = 4.dp, horizontal = 16.dp)
                                            .fillMaxWidth()
                                    )
                                    DividerView(
                                        modifier = Modifier.fillMaxWidth(),
                                        thickness = 1.dp
                                    )
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                        verticalArrangement = Arrangement.Top,
                                    ) {
                                        UserProfileData(
                                            icon = Icons.Default.DateRange,
                                            title = stringResource(R.string.last_login_date),
                                            description = lastLogin
                                        )
                                        UserProfileData(
                                            icon = Icons.Default.Cake,
                                            title = stringResource(R.string.account_created_by),
                                            description = createdBy
                                        )
                                        UserProfileData(
                                            icon = Icons.Default.DateRange,
                                            title = stringResource(R.string.account_created_date),
                                            description = createdDate
                                        )
                                        UserProfileData(
                                            icon = Icons.Default.Cake,
                                            title = stringResource(R.string.account_modify_by),
                                            description = modifyBy
                                        )
                                        UserProfileData(
                                            icon = Icons.Default.DateRange,
                                            title = stringResource(R.string.account_modify_date),
                                            description = modifyDate
                                        )
                                    }
                                }
                            }

                            // about details
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalArrangement = Arrangement.Top,
                            ) {
                                TextView(
                                    text = stringResource(id = R.string.about_us),
                                    textType = TextType.LARGE_TEXT_BOLD,
                                    color = black,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .padding(vertical = 4.dp, horizontal = 16.dp)
                                        .fillMaxWidth()
                                )
                                DividerView(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
                                TextView(
                                    text = aboutsUser,
                                    textType = TextType.BASE_TEXT_REGULAR,
                                    color = black,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
            PullRefreshIndicatorView(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = userDetailViewModel.isRefreshing,
                state = pullRefreshState
            )
        }
    }
}


// account delete or deactivate
@Composable
fun CustomAccountVerify(
    leadingIcon: ImageVector,
    title: String,
    isStatus: String?,
    text: String,
    color: Color,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Moved the Card and Text to start
        Card(
            modifier = Modifier
                .clip(CircleShape)
                .size(35.dp) // Added padding to separate from the text
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                VectorIconView(imageVector = leadingIcon, tint = primary, modifier = Modifier)
            }
        }

        // Use weight and fillMaxHeight to make the Column take up remaining space
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            TextView(
                text = title,
                textType = TextType.BASE_TEXT_SEMI_BOLD,
                color = textColor,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 4.dp)
            )
            TextView(
                text = isStatus ?: "N/S",
                textType = TextType.BASE_TEXT_REGULAR,
                color = textColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        TextView(
            text = text,
            color = color,
            textType = TextType.SMALL_TEXT_BOLD,
            modifier = Modifier.wrapContentSize(),
        )
    }
}