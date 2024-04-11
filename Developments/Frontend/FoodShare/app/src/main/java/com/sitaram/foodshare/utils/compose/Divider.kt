package com.sitaram.foodshare.utils.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sitaram.foodshare.theme.darkGray
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.lightGray

@Composable
fun DividerWithTextView(text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DividerView(modifier = Modifier.width(140.dp), thickness = 2.dp)
        TextView(text = text, color = darkGray, textType = TextType.LARGE_TEXT_REGULAR)
        DividerView(modifier = Modifier.width(140.dp), thickness = 2.dp)
    }
}

@Composable
fun DividerView(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.shadow(2.dp),
    thickness: Dp = 0.dp,
    color: Color = lightGray
) {
    Divider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}