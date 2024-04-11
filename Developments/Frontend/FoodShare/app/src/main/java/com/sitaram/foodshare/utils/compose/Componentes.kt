package com.sitaram.foodshare.utils.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sitaram.foodshare.R
import com.sitaram.foodshare.theme.blue
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.textColor
import com.sitaram.foodshare.theme.white

@Composable
fun NetworkIsNotAvailableView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VectorIconView(
            imageVector = Icons.Default.WifiOff,
            tint = primary,
            modifier = Modifier
                .size(70.dp)
                .padding(vertical = 16.dp)
        )
        TextView(
            text = stringResource(R.string.oops_unable_to_connect_to_the_network),
            textType = TextType.EXTRA_LARGE_TEXT_REGULAR,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        TextView(
            text = stringResource(R.string.you_must_connect_to_a_wi_fi_network_to_proceed),
            textType = TextType.BASE_TEXT_REGULAR,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Composable
fun ViewLocationInMap(
    pickUpLocation: String,
    description: String,
    onClickAction: (() -> Unit)? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, shape = ShapeDefaults.Small)
            .background(white)
            .clickable { onClickAction?.invoke() },
        colors = CardDefaults.cardColors(white),
    ) {
        // location details
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PainterImageView(
                painterImage = painterResource(id = R.mipmap.img_map),
                modifier = Modifier
                    .size(90.dp)
                    .scale(1f),
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VectorIconView(
                        imageVector = Icons.Default.LocationOn,
                        tint = blue,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .padding(4.dp)
                            .background(white, CircleShape)
                            .border(1.dp, blue, CircleShape)
                            .padding(horizontal = 2.dp)
                    )
                    TextView(
                        text = pickUpLocation,
                        textType = TextType.BASE_TEXT_BOLD,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                    )
                }
                TextView(
                    text = description,
                    textType = TextType.BASE_TEXT_REGULAR,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun DisplayErrorMessageView(
    text: String? = null,
    vectorIcon: ImageVector? = null,
    onClick: (() -> Unit)? = null,

) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextView(
            text = text ?: "No food details available",
            color = gray,
            textAlign = TextAlign.Center,
            textType = TextType.BASE_TEXT_REGULAR
        )
        if (vectorIcon != null) {
            IconButton(onClick = { onClick?.invoke() }) {
                VectorIconView(
                    imageVector = vectorIcon,
                    modifier = Modifier.size(25.dp),
                    tint = primary
                )
            }
        }
    }
}


@Composable
fun UserProfileContactView(
    trailingIcon: ImageVector,
    title: String,
    description: String? = null,
    leadingIcon: ImageVector,
    onClickCall: (() -> Unit?)? = null,
    tint: Color = blue
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
                VectorIconView(imageVector = trailingIcon, tint = primary, modifier = Modifier)
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
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
        androidx.compose.material.IconButton(
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
                imageVector = leadingIcon, //Icons.Default.Call,
                modifier = Modifier
                    .size(24.dp),
                tint = tint,
            )
        }
    }
}