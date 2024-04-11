package com.sitaram.foodshare.utils.compose

import android.annotation.SuppressLint
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import com.sitaram.foodshare.theme.gray
import com.sitaram.foodshare.theme.lightGray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.red
import com.sitaram.foodshare.theme.textColor

// input text field
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ModifierParameter")
@Composable
fun InputTextFieldView(
    value: String? = null,
    onValueChange: (String) -> Unit,
    label: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: @Composable() (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    isEmptyValue: Boolean = false,
    isInvalidValue: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    shape: Shape = ShapeDefaults.Medium,
    invalidMessage: String? = null,
    focusedBorderColor: Color = primary,
    errorColor: Color = Color.Unspecified,
    textStyle: TextStyle = TextStyle(
        fontWeight = findFontWeight(textType = TextType.INPUT_TEXT_VALUE),
        fontFamily = findFontFamily(textType = TextType.INPUT_TEXT_VALUE),
        fontSize = findTextSize(textType = TextType.INPUT_TEXT_VALUE).sp,
        lineHeight = findLineHeight(textType = TextType.INPUT_TEXT_VALUE).sp,
    ),
) {
    var isSelected by remember { mutableStateOf(false) }

    var labelColor by remember { mutableStateOf(gray) }
    labelColor = if (isEmptyValue) {
        isSelected = false
        errorColor
    } else if (isSelected) {
        primary
    } else {
        isSelected = false
        gray
    }

    var iconColor by remember { mutableStateOf(gray) }
    iconColor = if (isEmptyValue || isInvalidValue) {
        red
    } else {
        gray
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        OutlinedTextField(
            value = value ?: "",
            onValueChange = {
                onValueChange.invoke(it)
                isSelected = true
            },
            label = {
                TextView(
                    text = label ?: "",
                    color = labelColor,
                    textType = TextType.INPUT_TEXT_LABEL
                )
            },
            placeholder = {
                TextView(
                    text = placeholder ?: "",
                    textType = TextType.INPUT_TEXT_VALUE,
                    color = gray,
                )
            },
            textStyle = textStyle,
            trailingIcon = null,
            leadingIcon = { leadingIcon?.invoke() },
            enabled = enabled,
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,// KeyboardOptions(keyboardType = KeyboardType.Text), ,
            singleLine = singleLine,
            maxLines = maxLines,
            shape = shape,
            isError = (isEmptyValue || isInvalidValue),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = textColor,
                focusedBorderColor = focusedBorderColor,
                disabledBorderColor = lightGray,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled),
            ),
            modifier = modifier
        )
        if (isEmptyValue) {
            TextView(
                text = "The $label is empty!",
                textType = TextType.CAPTION_TEXT,
                color = errorColor,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
        if (isInvalidValue) {
            TextView(
                text = invalidMessage ?: "",
                textType = TextType.CAPTION_TEXT,
                color = errorColor,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

// password input text field
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ModifierParameter")
@Composable
fun PasswordTextFieldView(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    isEmptyValue: Boolean = false,
    isInvalidValue: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    shape: Shape = ShapeDefaults.Medium,
    errorMessage: String? = null,
    errorColor: Color = Color.Unspecified,
) {
    val context = LocalContext.current
    val contextState = rememberUpdatedState(context)

    var passwordVisibility by remember { mutableStateOf(false) }
    var isSelected by remember { mutableStateOf(false) }

    var labelColor by remember { mutableStateOf(gray) }
    labelColor = if (isEmptyValue) {
        isSelected = false
        errorColor
    } else if (isSelected) {
        primary
    } else {
        isSelected = false
        gray
    }

    var iconColor by remember { mutableStateOf(gray) }
    iconColor = if (isEmptyValue || isInvalidValue) {
        red
    } else {
        gray
    }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange.invoke(it)
                isSelected = true
            },
            label = {
                TextView(
                    text = label ?: "",
                    color = labelColor,
                    textType = TextType.INPUT_TEXT_LABEL
                )
            },
            placeholder = {
                TextView(
                    text = placeholder ?: "",
                    color = gray,
                    textType = TextType.INPUT_TEXT_VALUE,
                )
            },
            textStyle = TextStyle(
                fontWeight = findFontWeight(textType = TextType.INPUT_TEXT_VALUE),
                fontFamily = findFontFamily(textType = TextType.INPUT_TEXT_VALUE),
                fontSize = findTextSize(textType = TextType.INPUT_TEXT_VALUE).sp,
                lineHeight = findLineHeight(textType = TextType.INPUT_TEXT_VALUE).sp
            ),
            enabled = enabled,
            shape = shape,
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            maxLines = maxLines,
            leadingIcon = {
                leadingIcon?.invoke()
            },
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisibility = !passwordVisibility },
                ) {
                    if (isEmptyValue || isInvalidValue) {
                        VectorIconView(
                            imageVector = Icons.Default.WarningAmber,
                            tint = red,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    showKeyboard(contextState.value)
                                    passwordVisibility = true
                                }
                        )
                    } else {
                        VectorIconView(
                            imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            tint = if (passwordVisibility) primary else gray, // if (isEmptyValue || isError) red else gray
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            },
            isError = (isEmptyValue || isInvalidValue),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = textColor,
                focusedBorderColor = primary,
                disabledBorderColor = lightGray,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled)
            ),
            modifier = modifier,
        )

        if (isEmptyValue) {
            TextView(
                text = "The $label is empty!",
                color = errorColor,
                textType = TextType.CAPTION_TEXT,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )
        }
        if (isInvalidValue) {
            TextView(
                text = errorMessage ?: "",
                color = errorColor,
                textType = TextType.CAPTION_TEXT,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )
        }
    }
}

// Function to show the keyboard
@Suppress("DEPRECATION")
private fun showKeyboard(context: Context) {
    val inputMethodManager = getSystemService(context, InputMethodManager::class.java)
    inputMethodManager?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

// text field
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ModifierParameter")
@Composable
fun TextFieldView(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    isEmptyValue: Boolean = false,
    isInvalidValue: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    shape: Shape = ShapeDefaults.Medium,
    errorMessage: String? = null,
    errorColor: Color = Color.Unspecified,
) {
    var isSelected by remember { mutableStateOf(false) }

    var labelColor by remember { mutableStateOf(gray) }
    labelColor = if (isEmptyValue) {
        isSelected = false
        errorColor
    } else if (isSelected) {
        primary
    } else {
        isSelected = false
        gray
    }

    var iconColor by remember { mutableStateOf(gray) }
    iconColor = if (isEmptyValue || isInvalidValue) {
        red
    } else {
        gray
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange.invoke(it)
                isSelected = true
            },
            label = {
                TextView(
                    text = label ?: "",
                    color = labelColor,
                    textType = TextType.INPUT_TEXT_LABEL
                )
            },
            placeholder = {
                TextView(
                    text = placeholder.toString(),
                    color = gray,
                    textType = TextType.INPUT_TEXT_VALUE,
                )
            },
            textStyle = TextStyle(
                fontWeight = findFontWeight(textType = TextType.INPUT_TEXT_VALUE),
                fontFamily = findFontFamily(textType = TextType.INPUT_TEXT_VALUE),
                fontSize = findTextSize(textType = TextType.INPUT_TEXT_VALUE).sp,
                lineHeight = findLineHeight(textType = TextType.INPUT_TEXT_VALUE).sp
            ),
            enabled = enabled,
            shape = shape,
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            maxLines = maxLines,
            leadingIcon = { leadingIcon?.invoke() },
            trailingIcon = { trailingIcon?.invoke() },
            isError = (isEmptyValue || isInvalidValue),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = textColor,
                focusedBorderColor = primary,
                disabledBorderColor = lightGray,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled)
            ),
            modifier = modifier,
        )

        if (isEmptyValue) {
            TextView(
                text = "The $label is empty!",
                color = errorColor,
                textType = TextType.CAPTION_TEXT,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )
        }
        if (isInvalidValue) {
            TextView(
                text = errorMessage ?: "",
                color = errorColor,
                textType = TextType.CAPTION_TEXT,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )
        }
    }
}