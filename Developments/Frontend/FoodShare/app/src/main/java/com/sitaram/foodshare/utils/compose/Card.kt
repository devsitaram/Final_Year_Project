package com.sitaram.foodshare.utils.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sitaram.foodshare.R
import com.sitaram.foodshare.features.dashboard.home.presentation.DropdownMenusItems
import com.sitaram.foodshare.utils.ApiUrl
import com.sitaram.foodshare.theme.blue
import com.sitaram.foodshare.theme.darkGray
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.lightGray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white
import com.sitaram.foodshare.theme.yellow
import com.sitaram.foodshare.utils.ConverterUtil
import com.sitaram.foodshare.utils.ConverterUtil.getChipColorByStatus

@Composable
fun PainterCardView(
    image: Painter,
    text: String,
    description: String,
    color: Color,
    onClick: (() -> Unit)? = null
) {
    val transparentColor = color.copy(alpha = 0.10f) // Create a new color with 25% alpha
    Card(
        modifier = Modifier
            .padding(4.dp)
            .background(Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(transparentColor).clickable { onClick?.invoke() },
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularImageView(
                    painter = image,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    TextView(
                        text = text,
                        textType = TextType.LARGE_TEXT_SEMI_BOLD,
                        color = primary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    TextView(
                        text = description,
                        textType = TextType.BASE_TEXT_REGULAR,
                        color = darkGray,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun UserCardView(
    imageUrl: String? = null,
    title: String? = null,
    text: String? = null,
    userRole: String? = null,
    email: String? = null,
    color: Color,
    onClickCall: (() -> Unit)? = null,
    onClickEmail: (() -> Unit)? = null,
    @SuppressLint("ModifierParameter")
    modifier: Modifier = Modifier,
) {
    val transparentColor = color.copy(alpha = 0.50f) // Create a new color with 25% alpha
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(transparentColor),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImageView(
                    model = if (imageUrl?.isNotEmpty() == true) (ApiUrl.API_BASE_URL + imageUrl) else ApiUrl.PROFILE_URL,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(white)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    TextView(
                        text = title ?: "Unknow donor",
                        textType = TextType.LARGE_TEXT_SEMI_BOLD,
                        color = primary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    TextView(
                        text = text ?: "$email",
                        textType = TextType.BASE_TEXT_REGULAR,
                        color = textColor,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    if (userRole != null) {
                        TextView(
                            text = userRole,
                            textType = TextType.BASE_TEXT_REGULAR,
                            color = textColor,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
                if (text == null) {
                    IconButton(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(CircleShape)
                            .background(white)
                            .size(36.dp),
                        onClick = {
                            onClickEmail?.invoke()
                        }
                    ) {
                        VectorIconView(
                            imageVector = Icons.Default.Email,
                            modifier = Modifier
                                .size(24.dp),
                            tint = blue,
                        )
                    }
                } else {
                    IconButton(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(CircleShape)
                            .background(white)
                            .size(36.dp),
                        onClick = {
                            onClickCall?.invoke()
                        }
                    ) {
                        VectorIconView(
                            imageVector = Icons.Default.Call,
                            modifier = Modifier
                                .size(24.dp),
                            tint = blue,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentCardView(
    imageUrl: String? = null,
    rating: Int? = null,
    title: String? = null,
    donateLocation: String? = null,
    donationDate: String? = null,
    status: String? = null,
    isVolunteer: Boolean = false,
    isDonor: Boolean = false,
    onClickReport: (()-> Unit)? = null,
    onClickDelete: (()-> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    val chipColor = getChipColorByStatus(status?.lowercase()).copy(alpha = 0.70f)
    var expandedUpdate by remember { mutableStateOf(false) }
    val convertedDate = ConverterUtil.convertStringToDate(donationDate)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .shadow(2.dp, shape = ShapeDefaults.Small)
            .background(white),
        shape = ShapeDefaults.Small,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick?.invoke() }
                .background(white),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImageView(
                    model = if (imageUrl != null) (ApiUrl.API_BASE_URL + imageUrl) else ApiUrl.PROFILE_URL,
                    modifier = Modifier
                        .height(110.dp)
                        .width(90.dp)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 12.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Bottom,
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
                            text = title ?: "",
                            textType = TextType.LARGE_TEXT_SEMI_BOLD,
                            maxLines = 1,
                            color = darkGray,
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                    if (isVolunteer) {
                        VectorIconView(
                            imageVector = Icons.Default.DeleteForever,
                            tint = gray,
                            modifier = Modifier
                                .size(25.dp)
                                .clip(CircleShape)
                                .clickable { onClickDelete?.invoke() }
                        )
                    }
                    if (isDonor) {
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
                                if (status != "New") {
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
                                }
                                DropdownMenuItem(
                                    text = {
                                        DropdownMenusItems(
                                            text = stringResource(R.string.delete),
                                            painter = Icons.Default.DeleteForever
                                        )
                                    },
                                    onClick = {
                                        expandedUpdate = false
                                        onClickDelete?.invoke()
                                    }
                                )
                            }
                        }
                    }
                }

                TextView(
                    text = donateLocation ?: "",
                    textType = TextType.BASE_TEXT_REGULAR,
                    color = gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                if (convertedDate != null) {
                    TextView(
                        text = convertedDate,
                        textType = TextType.CAPTION_TEXT,
                        color = gray,
                        modifier = Modifier
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
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

                    ChipView(
                        text = status ?: "",
                        modifier = Modifier
                            .height(22.dp),
                        colors = ChipDefaults.chipColors(chipColor)
                    )
                }

            }
        }
    }
}