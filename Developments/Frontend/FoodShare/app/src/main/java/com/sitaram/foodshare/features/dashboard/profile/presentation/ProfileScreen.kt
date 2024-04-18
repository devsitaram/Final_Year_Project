package com.sitaram.foodshare.features.dashboard.profile.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.AttachEmail
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ModeEditOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.presentation.listOfFoods
import com.sitaram.foodshare.features.dashboard.profile.data.Profile
import com.sitaram.foodshare.features.dashboard.profile.domain.ProfileModelDAO
import com.sitaram.foodshare.utils.ApiUrl
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.utils.compose.InputTextFieldView
import com.sitaram.foodshare.theme.black
import com.sitaram.foodshare.theme.blue
import com.sitaram.foodshare.theme.cardColor
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.lightGray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.red
import com.sitaram.foodshare.theme.skyBlue
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.transparent
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.ConverterUtil.convertStringToDate
import com.sitaram.foodshare.utils.ConverterUtil.convertUriToFile
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.Validators.isValidPhoneNumber
import com.sitaram.foodshare.utils.compose.AsyncImagePainter
import com.sitaram.foodshare.utils.compose.AsyncImageView
import com.sitaram.foodshare.utils.compose.ButtonSize
import com.sitaram.foodshare.utils.compose.ButtonView
import com.sitaram.foodshare.utils.compose.ConfirmationDialogView
import com.sitaram.foodshare.utils.compose.DisplayErrorMessageView
import com.sitaram.foodshare.utils.compose.DropDownMenu
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.PainterImageView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.TopAppBarIconView
import com.sitaram.foodshare.utils.compose.VectorIconView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.io.File


@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun ProfileViewScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {

    // Check The Internet Connection
    val context = LocalContext.current
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available
    val instance = UserInterceptors(context)
    val auth = instance.getAuthenticate()

    val userProfileData = profileViewModel.profileState
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    var isBottomSheetExpanded by remember { mutableStateOf(false) }
    var isShowConfirmation by remember { mutableStateOf(false) }

    if (userProfileData.isLoading) {
        ProcessingDialogView()
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = profileViewModel.isRefreshing,
        onRefresh = { profileViewModel.getSwipeToRefresh() }
    )

    if (userProfileData.isLoading) {
        ProcessingDialogView()
    }

    LaunchedEffect(key1 = Unit, block = {
        scaffoldState.bottomSheetState.collapse()
    })

    LaunchedEffect(key1 = scaffoldState.bottomSheetState.isExpanded, block = {
        isBottomSheetExpanded = scaffoldState.bottomSheetState.isExpanded
    })

    LaunchedEffect(userProfileData.message){
        if (userProfileData.message != null){
            showToast(context, userProfileData.message)
            profileViewModel.clearMessage()
        }
    }

    // checkbox action
    val isOnClickButtonSheet: () -> Unit = {
        scope.launch {
            if (scaffoldState.bottomSheetState.isExpanded) {
                scaffoldState.bottomSheetState.collapse()
            } else {
                scaffoldState.bottomSheetState.expand()
            }
        }
    }

    var userId by remember { mutableIntStateOf(0) }
    var getNewProfileImg by remember { mutableStateOf<Uri?>(null) }
    var imageFile by remember { mutableStateOf<File?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            getNewProfileImg = uri
            uri?.let {
                imageFile = convertUriToFile(context, uri)
                isShowConfirmation = true
            }
        }
    )

    // // get image url
    if (isShowConfirmation){
        ConfirmationDialogView(
            title = stringResource(R.string.update_profile),
            descriptions = stringResource(R.string.are_your_sure_you_want_to_change_your_profile_picture),
            onDismiss = { isShowConfirmation = false },
            onConfirm = {
                MainScope().launch {
                    if (imageFile != null) {
                        profileViewModel.updateProfilePicture(userId, imageFile)
                    } else {
                        showToast(context, "Image is null")
                    }
                    isOnClickButtonSheet.invoke()
                    isShowConfirmation = false
                }
            }
        )
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetShape = ShapeDefaults.ExtraLarge,
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(white)
            ) {
                UpdateProfileButtonSheetField(
                    isOnClickButtonSheet = {
                        isOnClickButtonSheet.invoke()
                    },
                    onClickPickImage = {
                        galleryLauncher.launch("image/*")
                    },
                    onClickUpdateProfile = {
                        MainScope().launch {
                            isOnClickButtonSheet.invoke()
                            profileViewModel.updateProfileDetails(userId, it)
                        }.job
                    },
                    userProfileData = userProfileData.data?.userProfile,
                    getNewProfileImg = getNewProfileImg
                )
            }
        }
    ) {
        // this is the screen
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
            TopAppBarIconView(
                title = stringResource(R.string.profile),
                modifier = Modifier.shadow(1.5.dp),
                navigationIcon = { PainterImageView(painter = painterResource(id = R.mipmap.img_app_logo)) },
                backgroundColor = white,
                contentColor = black,
                vectorIcon = Icons.Default.ModeEditOutline,
                onClickAction = { isOnClickButtonSheet.invoke() },
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
                    if (userProfileData.error != null) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            VectorIconView(
                                imageVector = Icons.Default.PersonPin,
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(16.dp),
                                tint = gray,
                            )
                            DisplayErrorMessageView(
                                text = userProfileData.error,
                                vectorIcon = if (profileViewModel.isRefreshing) null else Icons.Default.Refresh,
                                onClick = { profileViewModel.getSwipeToRefresh() }
                            )
                        }
                    } else {
                        userProfileData.data?.userProfile?.let { it ->
                            userId = it.id ?: 0
                            // Update only the desired fields
                            val updateAuth = auth?.apply {
                                if (it.username != null) {
                                    username = it.username
                                }
                                if (it.photoUrl != null){
                                    profile = it.photoUrl
                                }
                            }

                            // Save the modified Authentication object back to Shared Preferences
                            val editor = instance.getPreInstEditor()
                            editor.putString("authentication", Gson().toJson(updateAuth))
                            editor.apply()

                            Column(
                                modifier = Modifier.fillMaxSize().background(cardColor),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                ViewProfileDetails(
                                    userId = userId,
                                    role = it.role ?: "",
                                    username = it.username ?: "User",
                                    email = it.email,
                                    address = it.address?.takeIf { it != "null" } ?: "N/A",
                                    contactNo = it.contactNumber?.takeIf { it != "null" } ?: "N/A",
                                    gender = it.gender?.takeIf { it != "null" } ?: "N/A",
                                    dateOfBirth = it.dateOfBirth ?: "N/A",
                                    aboutsUser = it.aboutsUser?.takeIf { it != "null" } ?: "N/A",
                                    profileUrl = it.photoUrl
                                )
                            }
                        }
                    }
                }
            }
        }

        if (isBottomSheetExpanded) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(black.copy(alpha = 0.5f))
                .clickable(onClick = { isOnClickButtonSheet.invoke() }))
        }
    }
}

@Composable
fun ViewProfileDetails(
    userId: Int? = null,
    username: String? = null,
    role: String? = null,
    email: String? = null,
    address: String? = null,
    contactNo: String? = null,
    gender: String? = null,
    dateOfBirth: String? = null,
    aboutsUser: String? = null,
    profileUrl: String? = null
) {
    val convertDate = convertStringToDate(dateOfBirth)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 45.dp), contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(40.dp, 40.dp, 0.dp, 0.dp)),
                colors = CardDefaults.cardColors(white),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(white)
                        .padding(vertical = 50.dp, horizontal = 16.dp),
                    verticalArrangement = Arrangement.Top,
                ) {
                    TextView(
                        text = username ?: stringResource(R.string.user),
                        textType = TextType.LARGE_TEXT_SEMI_BOLD,
                        color = textColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    )
                    TextView(
                        text = "Id No. $userId",
                        textType = TextType.LARGE_TEXT_SEMI_BOLD,
                        color = textColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    TextView(
                        text = stringResource(R.string.information),
                        textType = TextType.LARGE_TEXT_BOLD,
                        color = black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .fillMaxWidth()
                    )
                    Divider(modifier = Modifier.padding(bottom = 4.dp))
                    UserProfileData(
                        icon = Icons.Default.VerifiedUser,
                        title = stringResource(R.string.role),
                        description = role
                    )
                    UserProfileData(
                        icon = Icons.Default.AttachEmail,
                        title = stringResource(id = R.string.email),
                        description = email
                    )
                    UserProfileData(
                        icon = Icons.Default.LocationOn,
                        title = stringResource(id = R.string.address),
                        description = address
                    )
                    UserProfileData(
                        icon = Icons.Default.PhoneInTalk,
                        title = stringResource(R.string.contact_number),
                        description = contactNo
                    )
                    UserProfileData(
                        icon = Icons.Default.Person,
                        title = stringResource(id = R.string.gender),
                        description = gender
                    )
                    UserProfileData(
                        icon = Icons.Default.Cake,
                        title = stringResource(id = R.string.date_of_birth),
                        description = convertDate
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.Top,
                    ) {
                        TextView(
                            text = stringResource(R.string.about_us),
                            textType = TextType.LARGE_TEXT_BOLD,
                            color = black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .fillMaxWidth()
                        )
                        Divider()
                        TextView(
                            text = aboutsUser
                                ?: stringResource(R.string.please_add_more_details_about_yourself_to_your_profile),
                            textType = TextType.BASE_TEXT_REGULAR,
                            color = black,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            AsyncImageView(
                model = if(profileUrl == null) ApiUrl.PROFILE_URL else  (ApiUrl.API_BASE_URL + profileUrl),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(lightGray)
            )
        }
    }
}

@Composable
fun UserProfileData(
    icon: ImageVector,
    title: String,
    description: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .clip(CircleShape)
                .size(35.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                VectorIconView(imageVector = icon, tint = primary, modifier = Modifier)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
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
                text = description ?: "N/S",
                textType = TextType.BASE_TEXT_REGULAR,
                color = textColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun UpdateProfileButtonSheetField(
    isOnClickButtonSheet: () -> Unit,
    onClickPickImage: () -> Unit,
    onClickUpdateProfile: (ProfileModelDAO) -> Unit,
    userProfileData: Profile? = null,
    getNewProfileImg: Uri?,
) {

    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val listOfGender = listOf("Male", "Female")
    var isValidPhoneNumber by remember { mutableStateOf(false) }

    userProfileData?.let {
        var username by remember { mutableStateOf(userProfileData.username ?: "") }
        var address by remember { mutableStateOf(userProfileData.address ?: "") }
        var contactNum by remember { mutableStateOf(userProfileData.contactNumber ?: "") }
        val gender by remember { mutableStateOf(listOfFoods[selectedItemIndex]) }
        var dateOfBirth by remember { mutableStateOf(userProfileData.dateOfBirth ?: "") }
        var aboutsUser by remember { mutableStateOf(userProfileData.aboutsUser ?: "") }
        val profile by remember { mutableStateOf(userProfileData.photoUrl) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { isOnClickButtonSheet.invoke() }) {
                    VectorIconView(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        tint = gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
                TextView(
                    text = stringResource(R.string.update_your_profile),
                    textType = TextType.TITLE4,
                    color = textColor,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.wrapContentSize(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    AsyncImagePainter(
                        model = getNewProfileImg ?: if (profile?.isNotEmpty() == true) (ApiUrl.API_BASE_URL + profile) else ApiUrl.PROFILE_URL,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(0.5.dp, lightGray, CircleShape)
                            .shadow(3.dp, CircleShape, spotColor = transparent)
                    )
                    VectorIconView(
                        imageVector = Icons.Default.ModeEditOutline,
                        tint = blue,
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape)
                            .padding(4.dp)
                            .background(white, CircleShape)
                            .clickable {
                                onClickPickImage.invoke()
                            }
                            .border(1.dp, skyBlue, CircleShape)
                            .padding(4.dp) // Add padding to maintain 4dp distance from the border
                    )
                }
            }

            // design
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {

                InputTextFieldView(
                    value = username,
                    onValueChange = { username = it },
                    leadingIcon = {
                        VectorIconView(
                            imageVector = Icons.Default.PersonOutline,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = stringResource(R.string.username),
                    placeholder = stringResource(R.string.enter_username),
                    isEmptyValue = false,
                    errorColor = red,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .height(58.dp)
                )

                InputTextFieldView(
                    value = address,
                    onValueChange = { address = it },
                    leadingIcon = {
                        VectorIconView(
                            imageVector = Icons.Default.LocationOn,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = stringResource(id = R.string.address),
                    placeholder = stringResource(R.string.enter_address),
                    isEmptyValue = false,
                    errorColor = red,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .height(58.dp)
                )

                InputTextFieldView(
                    value = contactNum,
                    onValueChange = {
                        if (it.length <= 10 || it.length < contactNum.length) {
                            contactNum = it
                            isValidPhoneNumber = true
                        }
                    },
                    leadingIcon = {
                        VectorIconView(
                            imageVector = Icons.Default.PhoneInTalk,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = stringResource(R.string.phone),
                    placeholder = stringResource(R.string.enter_phone_no),
                    isEmptyValue = false,
                    errorColor = red,
                    isInvalidValue = if (contactNum.length == 10) false else isValidPhoneNumber,
                    invalidMessage = stringResource(R.string.invalid_contact_number),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .height(58.dp)
                )

                Spacer(modifier = Modifier.padding(top = 4.dp))
                DropDownMenu(
                    listOfItems = listOfGender,
                    selectedItemIndex = selectedItemIndex,
                    label = stringResource(R.string.gender),
                    onClickAction = { index ->
                        selectedItemIndex = index
                    }
                )

                InputTextFieldView(
                    value = dateOfBirth,
                    onValueChange = { dateOfBirth = it },
                    leadingIcon = {
                        VectorIconView(
                            imageVector = Icons.Default.Cake,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = stringResource(R.string.date_of_birth),
                    placeholder = stringResource(R.string.yyyy_mm_dd),
                    isEmptyValue = false,
                    errorColor = red,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .height(58.dp)
                )

                InputTextFieldView(
                    value = aboutsUser,
                    onValueChange = { aboutsUser = it },
                    leadingIcon = {
                        VectorIconView(
                            imageVector = Icons.Default.Description,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = stringResource(R.string.abouts),
                    placeholder = stringResource(R.string.enter_about_details),
                    isEmptyValue = false,
                    maxLines = 2,
                    singleLine = false,
                    errorColor = red,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 4.dp)
                )

                ButtonView(
                    onClick = {
                        val profileDTO = ProfileModelDAO(
                            username = username.trim(),
                            address = address.trim().ifEmpty { null },
                            contactNum = if (isValidPhoneNumber(contactNum)) contactNum else null,
                            gender = gender.trim(),
                            dob = dateOfBirth.trim().ifEmpty { null },
                            aboutsUser = aboutsUser.trim().ifEmpty { null },
                        )
                        onClickUpdateProfile.invoke(profileDTO)
                    },
                    btnText = stringResource(id = R.string.update),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary,
                    ),
                    enabled = username.isNotEmpty(),
                    buttonSize = ButtonSize.LARGE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                )
                Spacer(modifier = Modifier.padding(bottom = 40.dp))
            }
        }
    }
}
