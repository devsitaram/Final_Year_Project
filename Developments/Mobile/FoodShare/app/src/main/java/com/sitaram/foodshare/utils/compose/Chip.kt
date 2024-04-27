package com.sitaram.foodshare.utils.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipColors
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sitaram.foodshare.theme.lightGray
import com.sitaram.foodshare.theme.transparent
import com.sitaram.foodshare.theme.white

/**
 * Composable function to display a chip view.
 *
 * @param text The text to display in the chip.
 * @param textColor The color of the text.
 * @param textType The type of text style to apply.
 * @param modifier The modifier for the chip.
 * @param colors The colors for the chip.
 * @param onClick Callback for when the chip is clicked.
 * @param enabled Whether the chip is enabled or not.
 * @param interactionSource The interaction source for the chip.
 * @param shape The shape of the chip.
 * @param border The border stroke of the chip.
 * @param leadingIcon The leading icon of the chip.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChipView(
    text: String? = null,
    textColor: Color = white,
    textType: TextType = TextType.CAPTION_TEXT,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    colors: ChipColors = ChipDefaults.chipColors(lightGray),
    onClick: (()-> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: CornerBasedShape = RoundedCornerShape(percent = 50),
    border: BorderStroke? = BorderStroke(0.dp, transparent),
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    Chip(
        onClick = { onClick?.invoke() },
        colors = colors,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        shape = shape,
        border = border,
        leadingIcon = leadingIcon
    ) {
        TextView(
            text = text ?: "N/S",
            textType = textType,
            color = textColor,
        )
    }
}