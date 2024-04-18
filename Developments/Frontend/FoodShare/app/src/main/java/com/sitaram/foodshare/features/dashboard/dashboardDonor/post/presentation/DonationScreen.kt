package com.sitaram.foodshare.features.dashboard.dashboardDonor.post.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationModelDAO
import com.sitaram.foodshare.features.navigations.BtnNavScreen
import com.sitaram.foodshare.features.navigations.NavScreen
import com.sitaram.foodshare.utils.ApiUrl
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.theme.black
import com.sitaram.foodshare.theme.darkGray
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.green
import com.sitaram.foodshare.theme.lightGray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.red
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.ConverterUtil.convertUriToFile
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.compose.ButtonSize
import com.sitaram.foodshare.utils.compose.ClickableTextView
import com.sitaram.foodshare.utils.compose.DropDownMenu
import com.sitaram.foodshare.utils.compose.ErrorMessageDialogBox
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.NormalTextView
import com.sitaram.foodshare.utils.compose.OutlineButtonView
import com.sitaram.foodshare.utils.compose.PainterImageView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.TopAppBarIconView
import com.sitaram.foodshare.utils.compose.VectorIconView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.Calendar

@Composable
fun FoodPostViewScreen(navController: NavHostController) {
    val context = LocalContext.current

    var latitude by remember { mutableDoubleStateOf(0.0) }
    var longitude by remember { mutableDoubleStateOf(0.0) }

    LaunchedEffect(key1 = true) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        // Check if location permissions are granted
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // toast message
            return@LaunchedEffect
        }

        // Define location request parameters
        val locationRequest = LocationRequest.Builder(1L)
            .setIntervalMillis(1L)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        // Define location callback
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    latitude = location.latitude
                    longitude = location.longitude
                }
            }
        }

        // Request location updates
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    DonationViewScreen(latitude, longitude, navController)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DonationViewScreen(
    latitude: Double? = null,
    longitude: Double? = null,
    navController: NavHostController,
    donationViewModel: DonationViewModel = hiltViewModel(),
) {
    // Check The Internet Connection
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available

    val context = LocalContext.current
    val donationResult = donationViewModel.donationState
    val user = UserInterceptors(context)

    var expandedFab by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var selectedItemIndex by remember { mutableIntStateOf(0) }

    var foodName by remember { mutableStateOf("") }
    var descriptions by remember { mutableStateOf("") }
    val foodType by remember { mutableStateOf(listOfFoods[selectedItemIndex]) }
    var quantities by remember { mutableIntStateOf(1) }
    var expireTime by remember { mutableStateOf("00:00") }
    var pickUpLocation by remember { mutableStateOf("") }

    var file by remember { mutableStateOf<File?>(null) }
    var getImageUrl by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            getImageUrl = uri
            uri?.let {
                file = convertUriToFile(context, uri)
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
        mCalendar.get(Calendar.MINUTE), false
    )

    LaunchedEffect(key1 = foodName, key2 = pickUpLocation, key3 = getImageUrl){
        expandedFab = (foodName.isNotEmpty() && pickUpLocation.isNotEmpty() && getImageUrl != null)
    }

    if (donationResult.isLoading) {
        ProcessingDialogView()
    }

    LaunchedEffect(key1 = donationResult.error, block = {
        if (donationResult.error != null) {
            errorMessage = donationResult.error
            showDialog = true
        }
    })

    LaunchedEffect(key1 = donationResult.data, block = {
        if (donationResult.data?.isSuccess == true) {
            showToast(context, "${donationResult.data.message}")
            navController.navigate(BtnNavScreen.Home.route){
                popUpTo(NavScreen.FoodPostPage.route){
                    inclusive = true
                }
            }
        }
    })

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if (expandedFab) {
                        val donationFoodDetails = DonationModelDAO(
                                foodName = foodName.trim(),
                                foodTypes = foodType.trim(),
                                quantity = quantities,
                                pickUpLocation = pickUpLocation.trim(),
                                latitude = latitude,
                                longitude = longitude,
                                descriptions = descriptions.trim(),
                                createdBy = user.getUserName(),
                                status = "New".trim(),
                                expireTime = expireTime,
                                donor = user.getUserId(),
                        )
                        MainScope().launch {
                            if (file != null) {
                                donationViewModel.setDonationFoodDetail(file, donationFoodDetails)
                            } else {
                                showToast(context, context.getString(R.string.please_select_a_food_image))
                            }
                        }
                    }
                },
                expanded = expandedFab,
                icon = {
                    VectorIconView(
                        Icons.Filled.Add,
                        tint = if (expandedFab) darkGray else lightGray
                    )
                },
                text = {
                    TextView(
                        text = "Post",
                        textType = TextType.TITLE4,
                        color = if (expandedFab) darkGray else lightGray
                    )
                },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBarIconView(
                title = stringResource(R.string.food_donation),
                modifier = Modifier.shadow(1.5.dp),
                navigationIcon = { PainterImageView(painter = painterResource(id = R.mipmap.img_app_logo)) },
                backgroundColor = white,
                contentColor = black,
            )
            if (!isConnected) {
                NetworkIsNotAvailableView()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .scrollable(
                            state = rememberScrollState(),
                            orientation = Orientation.Vertical
                        )
                ) {
                    // View foodDetails image
                    Spacer(modifier = Modifier.padding(top = 4.dp))
                    TextView(
                        text = "Food Details",
                        textType = TextType.BASE_TEXT_BOLD,
                        modifier = Modifier.padding(horizontal = 2.dp, vertical = 4.dp)
                    )
                    // FoodDetails Name
                    NormalTextView(
                        value = foodName,
                        onValueChange = { foodName = it },
                        placeholder = {
                            TextView(
                                text = stringResource(R.string.food_name),
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
                    NormalTextView(
                        value = descriptions,
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
                        text = stringResource(R.string.food_types),
                        textType = TextType.BASE_TEXT_BOLD,
                        modifier = Modifier.padding(horizontal = 2.dp, vertical = 4.dp)
                    )
                    DropDownMenu(
                        listOfItems = listOfFoods,
                        selectedItemIndex = selectedItemIndex,
                        onClickAction = { index ->
                            selectedItemIndex = index
                        }
                    )

                    Spacer(modifier = Modifier.padding(top = 16.dp))
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
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    TextView(
                        text = stringResource(R.string.limited_time),
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
                                text = expireTime,
                                textType = TextType.SMALL_TEXT_BOLD,
                                color = textColor,
                                modifier = Modifier.padding(horizontal = 6.dp)
                            )
                        }
                        // open the time dialog box
                        ClickableTextView(annotatedText = stringResource(id = R.string.select),
                            textType = TextType.SMALL_TEXT_BOLD,
                            onClick = { mTimePickerDialog.show() }
                        )

                    }

                    // Address / pick up location
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextView(
                            text = stringResource(R.string.address),
                            textType = TextType.BASE_TEXT_BOLD,
                            modifier = Modifier
                                .padding(horizontal = 2.dp, vertical = 4.dp)
                                .weight(1f)
                        )
                        if (latitude != 0.0) {
                            TextView(
                                text = stringResource(R.string.verify),
                                textType = TextType.CAPTION_TEXT,
                                color = green
                            )
                        }
                    }
                    NormalTextView(
                        value = pickUpLocation,
                        onValueChange = { pickUpLocation = it },
                        placeholder = {
                            TextView(
                                text = stringResource(R.string.pick_up_location),
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
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    TextView(
                        text = stringResource(R.string.food_image_optional),
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
                                text = if (getImageUrl != null) getImageUrl.toString() else stringResource(R.string.take_a_food_image_from_the_gallery),
                                textType = TextType.BASE_TEXT_REGULAR,
                                color = textColor,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 6.dp)
                            )
                        }
                        ClickableTextView(
                            annotatedText = stringResource(R.string.select),
                            textType = TextType.SMALL_TEXT_BOLD,
                            onClick = {
                                galleryLauncher.launch("image/*")
                            }
                        )
                    }

                    // View foodDetails image
                    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                        PainterImageView(
                            painter = rememberAsyncImagePainter(model = getImageUrl ?: ApiUrl.FOOD_URL),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    galleryLauncher.launch("image/*")
                                },
                            contentScale = ContentScale.Crop
                        )
                        if (getImageUrl == null) {
                            OutlineButtonView(
                                onClick = { galleryLauncher.launch("image/*") },
                                btnText = "Choose",
                                buttonSize = ButtonSize.MEDIUM,
                                modifier = Modifier
                                    .border(1.dp, color = primary, shape = CircleShape)
                                    .height(40.dp),
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        ErrorMessageDialogBox(
            title = stringResource(R.string.error),
            descriptions = errorMessage,
            onDismiss = { showDialog = !showDialog },
            btnText = stringResource(R.string.okay),
            color = red,
        )
    }
}


// List of food types
val listOfFoods = listOf(
    "Others",
    "Cake",
    "Green Vegetables",
    "Biscuits & Chocolates",
    "Sweet Snack",
    "Stable Food",
    "Fruits",
    "Meets",
    "Water & Cold Drinks"
)