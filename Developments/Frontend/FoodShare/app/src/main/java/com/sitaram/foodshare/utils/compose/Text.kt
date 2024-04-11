package com.sitaram.foodshare.utils.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sitaram.foodshare.R
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.textColor

// Text View
@Composable
fun TextView(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = textColor,
    fontStyle: FontStyle = FontStyle.Normal,
    maxLines: Int = Integer.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    textType: TextType = TextType.BASE_TEXT_REGULAR,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontStyle = fontStyle,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        fontFamily = findFontFamily(textType),
        fontWeight = findFontWeight(textType),
        fontSize = findTextSize(textType).sp,
        lineHeight = findLineHeight(textType).sp,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

// On Clickable text
@SuppressLint("ModifierParameter")
@Composable
fun ClickableTextView(
    annotatedText: String,
    textType: TextType = TextType.BASE_TEXT_REGULAR,
    softWrap: Boolean = false,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    onClick: ((Int) -> Unit?)? = null,
    modifier: Modifier = Modifier,
    color: Color = primary
) {
    ClickableText(
        text = AnnotatedString(annotatedText),
        modifier = modifier,
        style = TextStyle(
            fontFamily = findFontFamily(textType),
            fontWeight = findFontWeight(textType),
            fontSize = findTextSize(textType).sp,
            lineHeight = findLineHeight(textType).sp,
            color = color
        ),
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = { onTextLayout?.invoke(it) },
        onClick = { onClick?.invoke(it) },
    )
}

// Text enum class
enum class TextType {
    TITLE1, TITLE2, TITLE3, TITLE4, HEADLINE, INPUT_TEXT_LABEL, INPUT_TEXT_VALUE, EXTRA_LARGE_TEXT_REGULAR, LARGE_TEXT_BOLD, LARGE_TEXT_SEMI_BOLD, LARGE_TEXT_REGULAR, BASE_TEXT_BOLD, BASE_TEXT_SEMI_BOLD, BASE_TEXT_REGULAR, SMALL_TEXT_BOLD, SMALL_TEXT_SEMI_BOLD, SMALL_TEXT_REGULAR, EXTRA_SMALL_TEXT_REGULAR, CAPTION_TEXT, BUTTON_TEXT_LARGE, BUTTON_TEXT_REGULAR, BUTTON_TEXT_SMALL,
}

// Find the font family
fun findFontFamily(textType: TextType): FontFamily {
    return when (textType) {
        TextType.TITLE1, TextType.TITLE2, TextType.HEADLINE, TextType.LARGE_TEXT_BOLD, TextType.BASE_TEXT_BOLD, TextType.SMALL_TEXT_BOLD -> FontFamily(
            Font(R.font.proximanovabold)
        )

        TextType.TITLE3, TextType.TITLE4, TextType.EXTRA_LARGE_TEXT_REGULAR, TextType.LARGE_TEXT_SEMI_BOLD, TextType.BASE_TEXT_SEMI_BOLD, TextType.SMALL_TEXT_SEMI_BOLD, TextType.BUTTON_TEXT_LARGE, TextType.BUTTON_TEXT_REGULAR, TextType.BUTTON_TEXT_SMALL -> FontFamily(
            Font(R.font.proximanovasemibold)
        )

        TextType.INPUT_TEXT_VALUE, TextType.INPUT_TEXT_LABEL, TextType.CAPTION_TEXT, TextType.SMALL_TEXT_REGULAR, TextType.BASE_TEXT_REGULAR, TextType.LARGE_TEXT_REGULAR, TextType.EXTRA_SMALL_TEXT_REGULAR -> FontFamily(
            Font(R.font.proximanovaregular)
        )
    }
}

// Find the text weight
fun findFontWeight(textType: TextType): FontWeight {
    return when (textType) {
        TextType.TITLE2, TextType.TITLE3, TextType.TITLE4, TextType.LARGE_TEXT_BOLD, TextType.BASE_TEXT_BOLD, TextType.SMALL_TEXT_BOLD, TextType.BUTTON_TEXT_LARGE -> FontWeight.Bold
        TextType.TITLE1, TextType.HEADLINE, TextType.LARGE_TEXT_SEMI_BOLD, TextType.BASE_TEXT_SEMI_BOLD, TextType.SMALL_TEXT_SEMI_BOLD, TextType.BUTTON_TEXT_REGULAR -> FontWeight.SemiBold
        else -> FontWeight.Normal
    }
}

// Find the text line height
fun findLineHeight(textType: TextType): Int {
    return when (textType) {
        TextType.TITLE1 -> 40
        TextType.TITLE2, TextType.TITLE3 -> 32
        TextType.TITLE4, TextType.INPUT_TEXT_VALUE, TextType.EXTRA_LARGE_TEXT_REGULAR, TextType.LARGE_TEXT_BOLD, TextType.LARGE_TEXT_SEMI_BOLD, TextType.LARGE_TEXT_REGULAR, TextType.BUTTON_TEXT_LARGE, TextType.BUTTON_TEXT_REGULAR, TextType.BUTTON_TEXT_SMALL -> 24
        TextType.HEADLINE, TextType.BASE_TEXT_BOLD, TextType.BASE_TEXT_SEMI_BOLD, TextType.BASE_TEXT_REGULAR -> 20
        TextType.INPUT_TEXT_LABEL, TextType.SMALL_TEXT_BOLD, TextType.SMALL_TEXT_SEMI_BOLD, TextType.SMALL_TEXT_REGULAR, TextType.CAPTION_TEXT -> 16
        else -> 18
    }
}

// Find the text size
fun findTextSize(textType: TextType): Int {
    return when (textType) {
        TextType.TITLE1 -> 32
        TextType.TITLE2 -> 24
        TextType.TITLE3, TextType.EXTRA_LARGE_TEXT_REGULAR -> 20
        TextType.TITLE4, TextType.LARGE_TEXT_BOLD, TextType.LARGE_TEXT_SEMI_BOLD, TextType.LARGE_TEXT_REGULAR, TextType.BUTTON_TEXT_LARGE -> 16
        TextType.HEADLINE, TextType.INPUT_TEXT_VALUE, TextType.BASE_TEXT_BOLD, TextType.BASE_TEXT_SEMI_BOLD, TextType.BASE_TEXT_REGULAR, TextType.BUTTON_TEXT_REGULAR -> 14
        TextType.INPUT_TEXT_LABEL, TextType.SMALL_TEXT_BOLD, TextType.SMALL_TEXT_SEMI_BOLD, TextType.SMALL_TEXT_REGULAR, TextType.CAPTION_TEXT, TextType.BUTTON_TEXT_SMALL -> 12
        else -> 10
    }
}

