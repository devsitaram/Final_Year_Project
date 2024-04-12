package com.sitaram.foodshare.utils.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
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
    content: @Composable (RowScope.() -> Unit)? = null,
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
            content = content
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
            content = content
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
            content = content
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
            content = content
        )
    }
}

@Composable
fun LargeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(primary),
    btnText: String,
    shape: Shape = ShapeDefaults.Medium,
    textType: TextType = TextType.BUTTON_TEXT_LARGE,
    enabled: Boolean = true,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = white,
    content: @Composable() (RowScope.() -> Unit)?
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
    shape: Shape = ShapeDefaults.Medium,
    textType: TextType = TextType.BUTTON_TEXT_REGULAR,
    enabled: Boolean = true,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = white,
    content: @Composable() (RowScope.() -> Unit)?
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
        TextView(text = btnText, textType = textType, color = textColors, modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp))
    }
}


@Composable
fun SmallButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(primary),
    btnText: String,
    shape: Shape = ShapeDefaults.Medium,
    textType: TextType = TextType.BUTTON_TEXT_SMALL,
    enabled: Boolean = true,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = white,
    content: @Composable() (RowScope.() -> Unit)?
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = colors,
        enabled = enabled,
        elevation = elevation,
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
    content: @Composable() (RowScope.() -> Unit)?
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
    content: @Composable() (RowScope.() -> Unit)? = null,
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
            content = content
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
            content = content
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
            content = content
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
            content = content
        )
    }
}

//    OutlinedButton(
//        onClick = { onClick() },
//        modifier = modifier,
//        colors = colors,
//        enabled = enabled,
//        elevation = elevation,
//        border = border,
//        contentPadding = contentPadding
//    ) {
//        TextView(text = btnText, textType = textType, color = textColors, modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
//    }

@Composable
fun LargeOutlineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(Color.Unspecified),
    btnText: String,
    textType: TextType = TextType.BUTTON_TEXT_LARGE,
    enabled: Boolean = true,
    shape: Shape = ShapeDefaults.Large,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = primary,
    content: @Composable() (RowScope.() -> Unit)? = null
) {
    OutlinedButton(
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
fun MediumOutlineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(Color.Unspecified),
    btnText: String,
    textType: TextType = TextType.BUTTON_TEXT_REGULAR,
    enabled: Boolean = true,
    shape: Shape = ShapeDefaults.Medium,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = primary,
    content: @Composable() (RowScope.() -> Unit)? = null
) {
    OutlinedButton(
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
fun SmallOutlineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(Color.Unspecified),
    btnText: String,
    textType: TextType = TextType.BUTTON_TEXT_SMALL,
    enabled: Boolean = true,
    shape: Shape = ShapeDefaults.Small,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = primary,
    content: @Composable() (RowScope.() -> Unit)? = null
) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier,
        colors = colors,
        enabled = enabled,
        elevation = elevation,
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
    shape: Shape = ShapeDefaults.Small,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textColors: Color = primary,
    content: @Composable() (RowScope.() -> Unit)? = null
) {
    OutlinedButton(
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

// text button
@Composable
fun TextButtonView(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    textType: TextType = TextType.BUTTON_TEXT_REGULAR,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.small,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable() (RowScope.() -> Unit)? = null
) {
    TextButton(
        onClick = { onClick() },
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
    ) {
        TextView(text = text, textType = textType, color = primary, modifier = Modifier)
    }
}

@Composable
fun DangerButtonView(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(red),
    btnText: String,
    textType: TextType = TextType.BUTTON_TEXT_REGULAR,
    enabled: Boolean = true,
    shape: Shape = ShapeDefaults.Medium,
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
        border = border,
        contentPadding = contentPadding
    ) {
        TextView(text = btnText, textType = textType, color = textColors, modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
    }
}

enum class ButtonSize {
    SMALL, MEDIUM, LARGE, NON
}