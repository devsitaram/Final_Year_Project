package com.sitaram.foodshare.utils.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.red
import com.sitaram.foodshare.theme.white

/**
 * This utility file contains composable functions for creating customized buttons in Jetpack Compose.
 * It includes functions for creating buttons of various sizes and styles, including outlined buttons and text buttons.
 * @param onClick The action to perform when the button is clicked.
 * @param modifier The modifier for the button.
 * @param colors The colors for the button.
 * @param btnText The text to display on the button.
 * @param enabled Whether the button is enabled.
 * @param elevation The elevation of the button.
 * @param contentPadding The padding around the button's content.
 * @param textColors The colors of the button's text.
 */
@Composable
fun ButtonView(
    btnText: String,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(primary),
    enabled: Boolean = true,
    elevation: ButtonElevation? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = white,
    buttonSize: ButtonSize = ButtonSize.NON,
    onClick: () -> Unit,
) {
    when (buttonSize){
        ButtonSize.LARGE -> LargeButton(
            onClick = onClick,
            modifier = modifier,
            colors = colors,
            btnText = btnText,
            enabled = enabled,
            elevation = elevation,
            contentPadding = contentPadding,
            textColors = textColors,
        )
        ButtonSize.MEDIUM -> MediumButton(
            onClick = onClick,
            modifier = modifier,
            colors = colors,
            btnText = btnText,
            enabled = enabled,
            elevation = elevation,
            contentPadding = contentPadding,
            textColors = textColors,
        )
        ButtonSize.SMALL -> SmallButton(
            onClick = onClick,
            modifier = modifier,
            colors = colors,
            btnText = btnText,
            enabled = enabled,
            elevation = elevation,
            contentPadding = contentPadding,
            textColors = textColors,
        )
        else -> RoundShapeButton(
            onClick = onClick,
            modifier = modifier,
            colors = colors,
            btnText = btnText,
            enabled = enabled,
            elevation = elevation,
            contentPadding = contentPadding,
            textColors = textColors,
        )
    }
}

/**
 * Composable function to create a large-sized button.
 * @param textType The type of text to display on the button.
 * @param border The border of the button.
 */
@Composable
fun LargeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(primary),
    btnText: String,
//    shape: Shape = ShapeDefaults.ExtraLarge,
    textType: TextType = TextType.BUTTON_TEXT_LARGE,
    enabled: Boolean = true,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = white,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = colors,
        enabled = enabled,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding
    ) {
        TextView(text = btnText, textType = textType, color = textColors, modifier = Modifier.padding(vertical = 14.dp, horizontal = 28.dp))
    }
}

@Composable
fun MediumButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(primary),
    btnText: String,
    shape: Shape = ShapeDefaults.ExtraLarge,
    textType: TextType = TextType.BUTTON_TEXT_REGULAR,
    enabled: Boolean = true,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = white,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = colors,
        enabled = enabled,
        elevation = elevation,
        shape = shape,
        border = border,
        contentPadding = contentPadding
    ) {
        TextView(text = btnText, textType = textType, color = textColors, modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp))
    }
}


@Composable
fun SmallButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(primary),
    btnText: String,
    shape: Shape = ShapeDefaults.ExtraLarge,
    textType: TextType = TextType.BUTTON_TEXT_SMALL,
    enabled: Boolean = true,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = white,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = colors,
        enabled = enabled,
        elevation = elevation,
        shape = shape,
        border = border,
        contentPadding = contentPadding,
        content = {
            TextView(text = btnText, textType = textType, color = textColors, modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
        }
    )
}

@Composable
fun RoundShapeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(primary),
    btnText: String,
    textType: TextType = TextType.BUTTON_TEXT_SMALL,
    enabled: Boolean = true,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = white,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = colors,
        enabled = enabled,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding
    ) {
        TextView(text = btnText, textType = textType, color = textColors, modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
    }
}

@Composable
fun OutlineButtonView(
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(Color.Unspecified),
    btnText: String,
    enabled: Boolean = true,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = primary,
    buttonSize: ButtonSize = ButtonSize.NON,
    onClick: () -> Unit,
) {
    when (buttonSize){
        ButtonSize.LARGE -> LargeOutlineButton(
            onClick = onClick,
            modifier = modifier,
            colors = colors,
            btnText = btnText,
            enabled = enabled,
            elevation = elevation,
            border = border,
            contentPadding = contentPadding,
            textColors = textColors,
        )
        ButtonSize.MEDIUM -> MediumOutlineButton(
            onClick = onClick,
            modifier = modifier,
            colors = colors,
            btnText = btnText,
            enabled = enabled,
            elevation = elevation,
            contentPadding = contentPadding,
            textColors = textColors,
        )
        ButtonSize.SMALL -> SmallOutlineButton(
            onClick = onClick,
            modifier = modifier,
            colors = colors,
            btnText = btnText,
            enabled = enabled,
            elevation = elevation,
            contentPadding = contentPadding,
            textColors = textColors,
        )
        else -> RoundShapeOutlineButton(
            onClick = onClick,
            modifier = modifier,
            colors = colors,
            btnText = btnText,
            enabled = enabled,
            elevation = elevation,
            contentPadding = contentPadding,
            textColors = textColors,
        )
    }
}

@Composable
fun LargeOutlineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(Color.Unspecified),
    btnText: String,
    textType: TextType = TextType.BUTTON_TEXT_LARGE,
    enabled: Boolean = true,
    shape: Shape = ShapeDefaults.ExtraLarge,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = primary,
) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier,
        colors = colors,
        enabled = enabled,
        elevation = elevation,
        shape = shape,
        border = border,
        contentPadding = contentPadding
    ) {
        TextView(text = btnText, textType = textType, color = textColors, modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
    }
}

@Composable
fun MediumOutlineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(Color.Unspecified),
    btnText: String,
    textType: TextType = TextType.BUTTON_TEXT_REGULAR,
    enabled: Boolean = true,
    shape: Shape = ShapeDefaults.ExtraLarge,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = primary,
) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier,
        colors = colors,
        shape = shape,
        enabled = enabled,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding
    ) {
        TextView(text = btnText, textType = textType, color = textColors, modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
    }
}

@Composable
fun SmallOutlineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(Color.Unspecified),
    btnText: String,
    textType: TextType = TextType.BUTTON_TEXT_SMALL,
    enabled: Boolean = true,
    shape: Shape = ShapeDefaults.ExtraSmall,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = primary,
) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier,
        colors = colors,
        enabled = enabled,
        elevation = elevation,
        shape = shape,
        border = border,
        contentPadding = contentPadding
    ) {
        TextView(text = btnText, textType = textType, color = textColors) //  modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    }
}

@Composable
fun RoundShapeOutlineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(Color.Unspecified),
    btnText: String,
    textType: TextType = TextType.BUTTON_TEXT_SMALL,
    enabled: Boolean = true,
    shape: Shape = ShapeDefaults.ExtraSmall,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = primary,
) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier,
        colors = colors,
        enabled = enabled,
        elevation = elevation,
        shape = shape,
        border = border,
        contentPadding = contentPadding
    ) {
        TextView(text = btnText, textType = textType, color = textColors, modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
    }
}

// text button
//@Composable
//fun TextButtonView(
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    text: String,
//    textType: TextType = TextType.BUTTON_TEXT_REGULAR,
//    enabled: Boolean = true,
//    shape: Shape = MaterialTheme.shapes.small,
//    colors: ButtonColors = ButtonDefaults.buttonColors(),
//    elevation: ButtonElevation? = null,
//    border: BorderStroke? = null,
//    contentPadding: PaddingValues = PaddingValues(0.dp),
//) {
//    TextButton(
//        onClick = { onClick() },
//        modifier = modifier,
//        enabled = enabled,
//        shape = shape,
//        colors = colors,
//        elevation = elevation,
//        border = border,
//        contentPadding = contentPadding,
//    ) {
//        TextView(text = text, textType = textType, color = primary, modifier = Modifier)
//    }
//}

@Composable
fun DangerButtonView(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(red),
    btnText: String,
    textType: TextType = TextType.BUTTON_TEXT_REGULAR,
    enabled: Boolean = true,
    shape: Shape = ShapeDefaults.ExtraLarge,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = white
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = colors,
        enabled = enabled,
        elevation = elevation,
        shape = shape,
        border = border,
        contentPadding = contentPadding
    ) {
        TextView(text = btnText, textType = textType, color = textColors, modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
    }
}

/**
 * Enum class representing different sizes for buttons.
 */
enum class ButtonSize {
    SMALL, MEDIUM, LARGE, NON
}