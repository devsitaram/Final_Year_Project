package com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.ModeEditOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.domain.HistoryCompletedDto
import com.sitaram.foodshare.helper.UserInterceptors
import com.sitaram.foodshare.theme.darkGray
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.lightGray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.theme.yellow
import com.sitaram.foodshare.utils.ApiUrl
import com.sitaram.foodshare.utils.NetworkObserver
import com.sitaram.foodshare.utils.UserInterfaceUtil.Companion.showToast
import com.sitaram.foodshare.utils.compose.AsyncImagePainter
import com.sitaram.foodshare.utils.compose.ButtonSize
import com.sitaram.foodshare.utils.compose.ButtonView
import com.sitaram.foodshare.utils.compose.ClickableTextView
import com.sitaram.foodshare.utils.compose.DisplayErrorMessageView
import com.sitaram.foodshare.utils.compose.NormalTextView
import com.sitaram.foodshare.utils.compose.OutlineButtonView
import com.sitaram.foodshare.utils.compose.PainterImageView
import com.sitaram.foodshare.utils.compose.ProgressIndicatorView
import com.sitaram.foodshare.utils.compose.TextType
import com.sitaram.foodshare.utils.compose.TextView
import com.sitaram.foodshare.utils.compose.TopAppBarView
import com.sitaram.foodshare.utils.compose.VectorIconView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CompletedFoodHistoryView(
    foodId: Int,
    title: String,
    email: String?,
    navController: NavHostController,
    completedFoodHistoryViewModel: CompletedFoodHistoryViewModel = hiltViewModel(),
) {
    // Check The Internet Connection
    val connection by NetworkObserver.connectivityState()
    val isConnected = connection === NetworkObserver.ConnectionState.Available

    val context = LocalContext.current
    val getUserRole = UserInterceptors(context).getUserRole()
    val getHistoryFoodDetails = completedFoodHistoryViewModel.historyState
    var expandedFab by remember { mutableStateOf(false) }
    var rating by remember { mutableIntStateOf(0) }
    var location by remember { mutableStateOf("") }
    var descriptions by remember { mutableStateOf("The food is successfully distributed.") }

    var selectedImages by remember { mutableStateOf(listOf<Pair<Uri, String>>()) }

    // Pick image with description
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris ->
            selectedImages += uris.map { uri -> uri to "" }
        }
    )

    LaunchedEffect(key1 = location, key2 = descriptions, key3 = selectedImages) {
        expandedFab = (location.isNotEmpty() && descriptions.isNotEmpty() && selectedImages.isNotEmpty())
    }

    // Function to create the email body combining text and image descriptions
    fun createEmailBody(): String {
        val imageDescriptions = selectedImages.joinToString("") { it.second }
        return "Location: $location \nDescriptions: $descriptions $imageDescriptions"
    }

    if (getHistoryFoodDetails.isLoading) {
        ProgressIndicatorView()
    }


    if (getHistoryFoodDetails.error != null) {
        DisplayErrorMessageView(
            text = getHistoryFoodDetails.error
        )
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if (!isConnected) {
                        showToast(context, context.getString(R.string.no_internet_found))
                    } else {
                        if (selectedImages.isNotEmpty()) {
                            if (location.isNotEmpty() && descriptions.isNotEmpty()) {
                                MainScope().launch {
                                    val completedDto = HistoryCompletedDto(
                                        id = foodId,
                                        descriptions = descriptions.trim(),
                                        location = location.trim(),
                                        rating = rating
                                    )
                                    completedFoodHistoryViewModel.getHistoryCompleted(completedDto)
                                }
                            } else {
                                showToast(context, "The field is empty!")
                            }
                        } else {
                            showToast(context, "Please choose the image")
                        }
                    }
                },
                expanded = expandedFab,
                icon = {
                    VectorIconView(
                        imageVector = Icons.Filled.Done,
                        tint = if (expandedFab) darkGray else lightGray
                    )
                },
                text = {
                    TextView(
                        text = stringResource(R.string.complete),
                        textType = TextType.TITLE4,
                        color = if (expandedFab) darkGray else lightGray
                    )
                },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(white),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBarView(
                title = title,
                color = textColor,
                backgroundColor = white,
                leadingIcon = Icons.Default.ArrowBackIosNew,
                trailingIcon = if (getUserRole.lowercase() == "donor") Icons.Default.ModeEditOutline else null,
                modifier = Modifier
                    .shadow(4.dp)
                    .fillMaxWidth(),
                onClickLeadingIcon = { navController.navigateUp() }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                TextView(
                    text = stringResource(R.string.distributed_location),
                    textType = TextType.BASE_TEXT_SEMI_BOLD,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                NormalTextView(
                    value = location,
                    onValueChange = { location = it },
                    placeholder = {
                        TextView(
                            text = stringResource(R.string.location),
                            textType = TextType.INPUT_TEXT_VALUE,
                            color = gray
                        )
                    },
                    isError = descriptions.isEmpty(),
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .height(54.dp)
                )

                TextView(
                    text = "Distributed Info",
                    textType = TextType.BASE_TEXT_SEMI_BOLD,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
                NormalTextView(
                    value = descriptions,
                    onValueChange = { descriptions = it },
                    placeholder = {
                        TextView(
                            text = stringResource(R.string.descriptions),
                            textType = TextType.INPUT_TEXT_VALUE,
                            color = gray
                        )
                    },
                    isError = descriptions.isEmpty(),
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .height(54.dp)
                )

                // Rating
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    TextView(
                        text = "Rating",
                        textType = TextType.BASE_TEXT_SEMI_BOLD,
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp)
                    )
                    val text = if (rating > 1) "Stars" else "Star"
                    TextView(
                        text = "$rating $text",
                        textType = TextType.BASE_TEXT_SEMI_BOLD,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(5) { index ->
                        val isFilled = index < rating
                        VectorIconView(
                            imageVector = if (isFilled) Icons.Default.Star else Icons.Default.StarBorder,
                            tint = if (isFilled) yellow else lightGray,
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    rating = if (isFilled) {
                                        if (rating == 1) 0 else index + 1
                                    } else {
                                        index + 1
                                    }
                                }
                        )
                    }
                }

                // View foodDetails image
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
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
                            text = stringResource(R.string.take_a_food_image_from_the_gallery),
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
                if (selectedImages.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        itemsIndexed(selectedImages) {index, (uri) ->
                            AsyncImagePainter(
                                model = uri,
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(horizontal = 4.dp)
                                    .padding(top = 4.dp).clickable {
                                        selectedImages = selectedImages.toMutableList().apply {
                                            removeAt(index)
                                        }
                                    },
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        PainterImageView(
                            painter = rememberAsyncImagePainter(model = ApiUrl.FOOD_URL),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    galleryLauncher.launch("image/*")
                                }
                                .padding(vertical = 8.dp),
                            contentScale = ContentScale.Crop
                        )
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

    LaunchedEffect(key1 = getHistoryFoodDetails.data) {
        if (getHistoryFoodDetails.data?.message != null) {
            val intentEmail = Intent(Intent.ACTION_SEND_MULTIPLE)
            intentEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Donation completed") // subject
            intentEmail.putExtra(Intent.EXTRA_TEXT, createEmailBody()) // descriptions
            intentEmail.type = "message/rfc822"

            // Add image attachments to the email
            val imageUris = selectedImages.map { it.first }
            intentEmail.putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(imageUris))
            context.startActivity(Intent.createChooser(intentEmail, "Choose an Email client: "))
            navController.navigateUp()
        }
        completedFoodHistoryViewModel.clearMessage()
    }
}