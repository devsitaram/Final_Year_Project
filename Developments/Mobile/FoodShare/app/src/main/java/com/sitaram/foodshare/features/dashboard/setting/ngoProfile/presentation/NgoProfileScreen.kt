package com.sitaram.foodshare.features.dashboard.setting.ngoProfile.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.SendToMobile
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sitaram.foodshare.R
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.theme.lightGray
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.utils.ApiUrl
import com.sitaram.foodshare.utils.ConverterUtil.convertStringToDate
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.compose.AsyncImageView
import com.sitaram.foodshare.utils.compose.DisplayErrorMessageView
import com.sitaram.foodshare.utils.compose.NetworkIsNotAvailableView
import com.sitaram.foodshare.utils.compose.ProcessingDialogView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.TopAppBarView
import com.sitaram.foodshare.utils.compose.VectorIconView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

@SuppressLint("AutoboxingStateCreation")
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun NgoProfileViewScreen(
    role: String?,
    mainNavController: NavHostController,
    ngoProfileViewModel: NgoProfileViewModel = hiltViewModel(),
) {

    // Check The Internet Connection
    val context = LocalContext.current

    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available
    val userRole = UserInterceptors(context).getUserRole()
    LaunchedEffect(key1 = Unit) {
        if (isConnected) {
            withContext(Dispatchers.IO) {
                async {
                    ngoProfileViewModel.getNgoProfile()
                    ngoProfileViewModel.getNumberOfData(role?.lowercase())
                }.await()
            }
        } else {
            showToast(context, context.getString(R.string.no_internet_connection))
        }
    }

    val getNgoProfile = ngoProfileViewModel.ngoProfileState
    val getNumberOfDataState = ngoProfileViewModel.numberOfDataState
    var foodNumber by remember { mutableStateOf(0) }
    var historyNumber by remember { mutableStateOf(0) }
    var reportNumber by remember { mutableStateOf(0) }
    var userNumber by remember { mutableStateOf(0) }
    var deviceNumber by remember { mutableStateOf(0) }

    if (getNgoProfile.isLoading || getNumberOfDataState.isLoading) {
        ProcessingDialogView()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopAppBarView(
            title = stringResource(R.string.ngo_profile),
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
        if (getNgoProfile.error != null || getNumberOfDataState.error != null) {
            DisplayErrorMessageView(
                text = getNgoProfile.error ?: getNumberOfDataState.error,
                vectorIcon = if (ngoProfileViewModel.isRefreshing) null else Icons.Default.Refresh,
                onClick = { ngoProfileViewModel.getSwipeToRefresh(role?.lowercase()) }
            )
        }

        if (!isConnected) {
            NetworkIsNotAvailableView()
        } else {
            getNumberOfDataState.data?.data?.let { numberOfData ->
                foodNumber = numberOfData.food ?: 0
                historyNumber = numberOfData.history ?: 0
                reportNumber = numberOfData.report ?: 0
                userNumber = numberOfData.user ?: 0
                deviceNumber = numberOfData.device ?: 0
            }
            getNgoProfile.data?.ngo?.let { ngo ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp), Arrangement.Start, Alignment.CenterVertically
                    ) {
                        AsyncImageView(
                            model = if (ngo.ngoStreamUrl == null) ApiUrl.PROFILE_URL else ngo.ngoStreamUrl,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .background(lightGray)
                        )
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)
                        ) {
                            TextView(
                                text = ngo.ngoName?.uppercase() ?: stringResource(R.string.food_company),
                                textAlign = TextAlign.Start,
                                textType = TextType.LARGE_TEXT_BOLD,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            TextView(
                                text = ngo.ngoEmail?.uppercase() ?: "",
                                textAlign = TextAlign.Start,
                                textType = TextType.BASE_TEXT_REGULAR,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            TextView(
                                text = ngo.ngoContact ?: "",
                                textAlign = TextAlign.Start,
                                textType = TextType.BASE_TEXT_REGULAR,
                                modifier = Modifier
                            )
                        }
                    }

                    convertStringToDate(ngo.establishedDate)?.let {
                        TextView(text = it, textType = TextType.BASE_TEXT_SEMI_BOLD, modifier = Modifier.padding(bottom = 4.dp))
                    }
                    TextView(text = ngo.ngoLocation ?: "", textType = TextType.BASE_TEXT_SEMI_BOLD, modifier = Modifier.padding(bottom = 8.dp))
                    TextView(text = ngo.aboutsNgo ?: "", textType = TextType.BASE_TEXT_REGULAR, modifier = Modifier.padding(bottom = 4.dp))

                    // number of data
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)) {
                        CustomDataCardView(
                            numberOfData = userNumber,
                            imageVector = Icons.Default.PersonAddAlt,
                            title = stringResource(id = R.string.users),
                            descriptions = stringResource(R.string.latest_number_of_application_user)
                        )
                        CustomDataCardView(
                            numberOfData = foodNumber,
                            imageVector = Icons.Default.Fastfood,
                            title = stringResource(R.string.foods),
                            descriptions = stringResource(R.string.latest_number_of_donate_food)
                        )
                        CustomDataCardView(
                            numberOfData = historyNumber,
                            imageVector = Icons.Default.History,
                            title = stringResource(R.string.histories),
                            descriptions = stringResource(R.string.latest_number_of_history)
                        )
                        CustomDataCardView(
                            numberOfData = reportNumber,
                            imageVector = Icons.Default.Report,
                            title = stringResource(R.string.reports),
                            descriptions = stringResource(R.string.latest_number_of_report)
                        )
                        if (userRole.lowercase() == "admin"){
                            CustomDataCardView(
                                numberOfData = deviceNumber,
                                imageVector = Icons.AutoMirrored.Filled.SendToMobile,
                                title = stringResource(R.string.number_of_devices),
                                descriptions = stringResource(R.string.latest_number_device_for_notification)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomDataCardView(
    numberOfData: Int,
    imageVector: ImageVector,
    title: String,
    descriptions: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .shadow(2.dp, shape = ShapeDefaults.Small)
            .background(white),
        colors = CardDefaults.cardColors(white)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            VectorIconView(
                imageVector = imageVector,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .padding(2.dp)
            )

            // Use weight and fillMaxHeight to make the Column take up remaining space
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                TextView(
                    text = "Total $title: $numberOfData",
                    textType = TextType.LARGE_TEXT_BOLD,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                TextView(
                    text = descriptions,
                    textType = TextType.BASE_TEXT_REGULAR,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )
            }
        }
    }
}