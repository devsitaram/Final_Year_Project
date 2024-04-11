package com.sitaram.foodshare.utils.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextOverflow
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.white

@Composable
fun CheckboxView(
    text: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textType: TextType = TextType.BUTTON_TEXT_REGULAR,
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkmarkColor = white, // Customize checkmark color if needed
        checkedColor = primary, // Set checked background tint color
        uncheckedColor = gray, // Set unchecked background tint color
        disabledCheckedColor = gray, // Set disabled checked color
        disabledUncheckedColor = gray,
    ),
    checked: Boolean, // Remove the remember here
    onCheckedChange: ((Boolean) -> Unit)? = null // Update the onClick lambda to pass the Boolean
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                if (onCheckedChange != null) {
                    onCheckedChange(it)
                }
            }, // Pass the updated value to the onClick lambda
            modifier = Modifier.scale(0.9f),
            enabled = enabled,
            colors = colors
        )
        if (text != null) {
            ClickableTextView(
                annotatedText = text, //"Remember Me",
                color = if (checked) primary else gray,
                textType = textType,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .wrapContentWidth()
                    .clickable {
                        if (onCheckedChange != null) {
                            onCheckedChange(!checked)
                        }
                    },
            )
        }
    }
}