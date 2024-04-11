package com.sitaram.foodshare.features.dashboard.foodDetail.presentation

import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.ModeEditOutline
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.theme.backgroundLayoutColor
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.UserInterfaceUtil
import com.sitaram.foodshare.utils.compose.UserCardView
import com.sitaram.foodshare.utils.compose.DividerView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.TopAppBarView
import com.sitaram.foodshare.utils.compose.VectorIconView
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationModelDAO
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.presentation.listOfFoods
import com.sitaram.foodshare.utils.ApiUrl
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.source.local.FoodsEntity
import com.sitaram.foodshare.theme.cardColor
import com.sitaram.foodshare.theme.darkGray
import com.sitaram.foodshare.theme.green
import com.sitaram.foodshare.theme.lightGray
import com.sitaram.foodshare.theme.yellow
import com.sitaram.foodshare.utils.ConverterUtil.convertStringToDate
import com.sitaram.foodshare.utils.ConverterUtil.convertUriToFile
import com.sitaram.foodshare.utils.ConverterUtil.getChipColorByStatus
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.getPhoneCall
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.compose.AsyncImagePainter
import com.sitaram.foodshare.utils.compose.AsyncImageView
import com.sitaram.foodshare.utils.compose.ButtonSize
import com.sitaram.foodshare.utils.compose.ButtonView
import com.sitaram.foodshare.utils.compose.ChipView
import com.sitaram.foodshare.utils.compose.ClickableTextView
import com.sitaram.foodshare.utils.compose.ConfirmationDialogView
import com.sitaram.foodshare.utils.compose.DisplayErrorMessageView
import com.sitaram.foodshare.utils.compose.DropDownMenu
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.NormalTextView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.ViewLocationInMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.Calendar

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun FoodDetailsViewScreen(
    id: Int,
    title: String?,
    rating: Int?,
    navController: NavHostController,
    foodDetailViewModel: FoodDetailViewModel = hiltViewModel(),
) {

    // Check The Internet Connection
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available

    val context = LocalContext.current
    val getFoodDetails = foodDetailViewModel.foodDetailState
    val interpolator = UserInterceptors(context)
    val userRole = interpolator.getUserRole()
    val userId = interpolator.getUserId()

    var foodId by remember { mutableIntStateOf(0) }
    var isUpdateDonateFood by remember { mutableStateOf(false) }
    var isShowConfirmation by remember { mutableStateOf(false) }
    var fileImage by remember { mutableStateOf<File?>(null) }

    LaunchedEffect(key1 = foodDetailViewModel, block = {
        if (isConnected) {
            foodDetailViewModel.getFoodDetailState(id)
        }
    })


    if (getFoodDetails.isLoading) {
        ProcessingDialogView()
    }

    if (getFoodDetails.error != null) {
        DisplayErrorMessageView(
            text = getFoodDetails.error,
            vectorIcon = Icons.Default.Refresh,
            onClick = { foodDetailViewModel.getSwipeToRefresh() }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBarView(
            title = title ?: "Food Details",
            color = textColor,
            backgroundColor = white,
            leadingIcon = Icons.Default.ArrowBackIosNew,
            trailingIcon = if (userRole.lowercase() == "donor" && getFoodDetails.data?.userId == userId) Icons.Default.ModeEditOutline else null,
            modifier = Modifier
                .shadow(4.dp)
                .fillMaxWidth(),
            onClickLeadingIcon = { navController.navigateUp() },
            onClickTrailingIcon = { isUpdateDonateFood = !isUpdateDonateFood }
        )
        DividerView(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)

        if (!isConnected) {
            NetworkIsNotAvailableView()
        } else {
            getFoodDetails.data?.let {
                if (!isUpdateDonateFood) {
                    ViewFoodDetails(
                        food = it,
                        rating = rating,
                        onClickViewMap = {
                            navController.navigate("GoogleMapView/${it.latitude.toString()}/${it.longitude.toString()}/${it.username}")
                        },
                        onClickViewProfile = {
                            navController.navigate("UserDetailView/${it.userId}")
                        },
                        context = context,
                    )
                } else {
                    ViewEditDonateFoodDetails(
                        food = it,
                        onClickFoodUpdate = { it1 ->
                            // Update the donate food
                            MainScope().launch {
                                foodDetailViewModel.getUpdateDonateFood(it.id, it1, context)
                                isUpdateDonateFood = false
                            }
                        },
                        onClickImageUpdate = { it1 ->
                            foodId = it.id ?: 0
                            fileImage = it1
                            isShowConfirmation = true
                        },
                        context = context
                    )
                }
            }
        }
    }

    if (isShowConfirmation) {
        ConfirmationDialogView(
            title = "Change Image",
            descriptions = "Are your sure you want to change food image!",
            onDismiss = { isShowConfirmation = false },
            onConfirm = {
                MainScope().launch {
                    foodDetailViewModel.getUpdateDonateFoodImage(foodId, fileImage, context)
                    isUpdateDonateFood = false
                    isShowConfirmation = false
                }
            }
        )
    }

    LaunchedEffect(getFoodDetails.message){
        if (getFoodDetails.message != null){
            showToast(context, getFoodDetails.message)
            foodDetailViewModel.clearMessage()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ViewFoodDetails(
    food: FoodsEntity,
    rating: Int? = 0,
    onClickViewMap: (() -> Unit)? = null,
    onClickViewProfile: (() -> Unit)? = null,
    context: Context,
    isBtnVisible: Boolean? = false,
    status: String? = null,
    onClickAcceptBtn: (() -> Unit)? = null,
    onClickCompletedBtn: (() -> Unit)? = null,
) {
    val chipColor = getChipColorByStatus(food.status?.lowercase()).copy(alpha = 0.70f)
    val getConvertDate = convertStringToDate(food.modifyDate ?: food.createdDate)
    val interceptors = UserInterceptors(context)
    val username = interceptors.getUserName()
    val email = interceptors.getUserEmail()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(backgroundLayoutColor)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TextView(
                text = "Donation Info",
                modifier = Modifier
                    .weight(1f),
                textType = TextType.LARGE_TEXT_BOLD,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                repeat(5) { index ->
                    VectorIconView(
                        imageVector = if (index < (rating
                                ?: 0)
                        ) Icons.Default.Star else Icons.Default.StarBorder,
                        tint = if (index < (rating ?: 0)) yellow else lightGray,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 6.dp)
                    )
                }
            }
        }

        // view map
        ViewLocationInMap(
            pickUpLocation = food.pickUpLocation ?: "Unknown",
            description = "View Location in Map",
            onClickAction = {
                onClickViewMap?.invoke()
            }
        )

        // donor profiles
        UserCardView(
            imageUrl = food.photoUrl,
            title = food.username,
            text = food.contactNumber,
            userRole = if (food.username == username) "Self ($username)" else "Others",
            email = email,
            color = cardColor,
            onClickCall = {
                getPhoneCall(food.contactNumber, context)
            },
            onClickEmail = {
                if (email.isNotEmpty() && email != "N/S") {
                    UserInterfaceUtil.getEmailSend(email, context)
                }
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .background(Color.Transparent)
                .clickable {
                    onClickViewProfile?.invoke()
                }
        )

        // donation status
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextView(
                text = "Status",
                textType = TextType.BASE_TEXT_SEMI_BOLD,
                modifier = Modifier.weight(1f)
            )
            ChipView(
                text = food.status,
                textType = TextType.SMALL_TEXT_SEMI_BOLD,
                colors = ChipDefaults.chipColors(chipColor),
                modifier = Modifier.height(22.dp),
            )
        }

        // food details
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = CardDefaults.elevatedShape,
            colors = CardDefaults.cardColors(white)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    AsyncImageView(
                        model = if (food.streamUrl != null) (ApiUrl.API_BASE_URL + food.streamUrl) else ApiUrl.FOOD_URL,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    TextView(
                        text = "Name: ${food.foodName}",
                        textType = TextType.BASE_TEXT_SEMI_BOLD,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    TextView(
                        text = "Type: ${food.foodTypes}",
                        textType = TextType.BASE_TEXT_SEMI_BOLD,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    TextView(
                        text = "Quantity: ${food.quantity}",
                        textType = TextType.BASE_TEXT_SEMI_BOLD,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextView(
                            text = "Exp Time: ${food.expireTime}",
                            textType = TextType.BASE_TEXT_SEMI_BOLD,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                        if (getConvertDate != null) {
                            TextView(
                                text = getConvertDate,
                                textType = TextType.CAPTION_TEXT,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        // description
        TextView(
            text = "Note",
            textType = TextType.BASE_TEXT_BOLD,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth()
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            TextView(
                text = food.descriptions
                    ?: stringResource(R.string.we_donate_and_distribute_food_to_support),
                textType = TextType.BASE_TEXT_REGULAR,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(10.dp)
            )
        }
        if (isBtnVisible == true) {
            if (status?.lowercase() == "new") {
                ButtonView(
                    onClick = {
                        onClickAcceptBtn?.invoke()
                    },
                    btnText = stringResource(R.string.accept),
                    modifier = Modifier.fillMaxWidth(),
                    buttonSize = ButtonSize.LARGE
                )
            } else {
                ButtonView(
                    onClick = {
                        onClickCompletedBtn?.invoke()
                    },
                    btnText = stringResource(R.string.completed),
                    modifier = Modifier.fillMaxWidth(),
                    buttonSize = ButtonSize.LARGE
                )
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
    }
}

@Composable
fun ViewEditDonateFoodDetails(
    food: FoodsEntity,
    onClickFoodUpdate: (DonationModelDAO?) -> Unit,
    onClickImageUpdate: (File?) -> Unit,
    context: Context,
) {

    val user = UserInterceptors(context)
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    var expandedDropDownMenu by remember { mutableStateOf(false) }

    var foodName by remember { mutableStateOf(food.foodName) }
    var descriptions by remember { mutableStateOf(food.descriptions) }
    val foodType by remember { mutableStateOf(food.foodTypes ?: listOfFoods[selectedItemIndex]) }
    var quantities by remember { mutableIntStateOf(food.quantity ?: 1) }
    var expireTime by remember { mutableStateOf(food.expireTime) }
    var pickUpLocation by remember { mutableStateOf(food.pickUpLocation) }

    var getNewImageUrl by remember { mutableStateOf<Uri?>(null) }
    var fileImage by remember { mutableStateOf<File?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                getNewImageUrl = uri
                fileImage = convertUriToFile(context, uri)
                if (fileImage != null) {
                    onClickImageUpdate.invoke(fileImage)
                }
            }
        }
    )
    // Initialize a Calendar and get time
    val mCalendar = Calendar.getInstance()
    val mTimePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            // Format the selected time with AM or PM
            val hour = if (hourOfDay < 12) hourOfDay else hourOfDay - 12
            val amPm = if (hourOfDay < 12) "AM" else "PM"
            expireTime = String.format("%02d:%02d %s", hour, minute, amPm)
        },
        mCalendar.get(Calendar.HOUR_OF_DAY),
        mCalendar.get(Calendar.MINUTE),
        false
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundLayoutColor)
            .padding(horizontal = 16.dp)
            .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // View foodDetails image
        Spacer(modifier = Modifier.padding(top = 4.dp))
        TextView(
            text = "Food Name",
            textType = TextType.BASE_TEXT_BOLD,
            modifier = Modifier.padding(start = 2.dp, top = 6.dp)
        )
        // FoodDetails Name
        NormalTextView(
            value = foodName ?: "",
            onValueChange = { foodName = it },
            placeholder = {
                TextView(
                    text = "Food Name",
                    textType = TextType.INPUT_TEXT_VALUE,
                    color = gray
                )
            },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .height(54.dp)
        )

        // Descriptions
        TextView(
            text = "Descriptions",
            textType = TextType.BASE_TEXT_BOLD,
            modifier = Modifier.padding(start = 2.dp, top = 4.dp)
        )
        NormalTextView(
            value = descriptions ?: "",
            onValueChange = { descriptions = it },
            placeholder = {
                TextView(
                    text = "Descriptions",
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

        // FoodDetails Types
        Spacer(modifier = Modifier.padding(top = 8.dp))
        TextView(
            text = "Food types",
            textType = TextType.BASE_TEXT_BOLD,
            modifier = Modifier.padding(horizontal = 2.dp, vertical = 4.dp)
        )
        DropDownMenu(
            listOfArray = listOfFoods,
            selectedItemIndex = selectedItemIndex,
            expanded = expandedDropDownMenu,
            onExpandedChange = { expandedDropDownMenu = !expandedDropDownMenu },
            onDismissRequest = { expandedDropDownMenu = false },
            onClickAction = { index ->
                selectedItemIndex = index
                expandedDropDownMenu = false
            }
        )

        Spacer(modifier = Modifier.padding(top = 18.dp))
        TextView(
            text = "Quantity",
            textType = TextType.BASE_TEXT_BOLD,
            modifier = Modifier.padding(horizontal = 2.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                VectorIconView(imageVector = Icons.Default.PeopleOutline, tint = gray)
                TextView(
                    text = "$quantities Persons",
                    textType = TextType.SMALL_TEXT_BOLD,
                    color = textColor,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
            }
            IconButton(onClick = { if (quantities > 0) quantities -= 1 }) {
                VectorIconView(imageVector = Icons.Default.Remove, tint = darkGray)
            }
            IconButton(onClick = { quantities += 1 }) {
                VectorIconView(imageVector = Icons.Default.Add, tint = darkGray)
            }
        }

        // expire data and time
        Spacer(modifier = Modifier.padding(top = 12.dp))
        TextView(
            text = "Limited-Time",
            textType = TextType.BASE_TEXT_BOLD,
            modifier = Modifier.padding(horizontal = 2.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                VectorIconView(imageVector = Icons.Default.AccessTime, tint = gray)
                TextView(
                    text = expireTime ?: "",
                    textType = TextType.SMALL_TEXT_BOLD,
                    color = textColor,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
            }
            // open the time dialog box
            ClickableTextView(annotatedText = "Select",
                textType = TextType.SMALL_TEXT_BOLD,
                onClick = { mTimePickerDialog.show() }
            )

        }

        // Address / pick up location
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TextView(
                text = "Pick-Up Location",
                textType = TextType.BASE_TEXT_BOLD,
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 4.dp)
                    .weight(1f)
            )
            if (food.latitude != 0.0) {
                TextView(
                    text = "Verify",
                    textType = TextType.CAPTION_TEXT,
                    color = green
                )
            }
        }
        NormalTextView(
            value = pickUpLocation ?: "",
            onValueChange = { pickUpLocation = it },
            placeholder = {
                TextView(
                    text = "Pick up Location",
                    textType = TextType.INPUT_TEXT_VALUE,
                    color = gray
                )
            },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .height(54.dp)
        )

        // foodDetails image
        Spacer(modifier = Modifier.padding(top = 12.dp))
        TextView(
            text = "Food's Image (Optional)",
            textType = TextType.BASE_TEXT_BOLD,
            modifier = Modifier.padding(horizontal = 2.dp, vertical = 4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { galleryLauncher.launch("image/*") },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                VectorIconView(imageVector = Icons.Default.Image, tint = gray)
                TextView(
                    text = "FoodDetails",
                    textType = TextType.SMALL_TEXT_BOLD,
                    color = textColor,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
            }
            ClickableTextView(
                annotatedText = "Select",
                textType = TextType.SMALL_TEXT_BOLD,
                onClick = {
                    galleryLauncher.launch("image/*")
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AsyncImageView(
                model = if (food.streamUrl != null) (ApiUrl.API_BASE_URL + food.streamUrl) else ApiUrl.FOOD_URL,
                modifier = Modifier.size(100.dp)
            )
            AsyncImagePainter(
                model = getNewImageUrl,
                modifier = Modifier.size(100.dp)
            )
        }

        ButtonView(
            btnText = stringResource(R.string.update),
            onClick = {
                val donationFoodDetails = DonationModelDAO(
                    foodName = foodName?.trim(),
                    foodTypes = foodType.trim(),
                    quantity = quantities,
                    pickUpLocation = pickUpLocation?.trim(),
                    latitude = food.latitude,
                    longitude = food.longitude,
                    descriptions = descriptions?.trim(),
                    modifyBy = user.getUserName(),
                    status = food.status,
                    expireTime = expireTime,
                    donor = user.getUserId(),
                )
                onClickFoodUpdate.invoke(donationFoodDetails)
            },
            buttonSize = ButtonSize.LARGE,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
    }
}