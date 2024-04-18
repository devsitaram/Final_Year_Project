package com.sitaram.foodshare.utils.compose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sitaram.foodshare.theme.gray


/**
 * Composable function to display a Switch.
 *
 * @param checked The current checked state of the Switch.
 * @param onCheckedChange Callback to be invoked when the checked state of the Switch changes.
 * @param modifier The modifier to be applied to the Switch.
 * @param enabled Whether the Switch is enabled or disabled.
 * @param colors The colors to be used for the Switch.
 * @param interactionSource The interaction source of the Switch.
 */
@Composable
fun SwitchView(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(gray),
    interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource
    )
}