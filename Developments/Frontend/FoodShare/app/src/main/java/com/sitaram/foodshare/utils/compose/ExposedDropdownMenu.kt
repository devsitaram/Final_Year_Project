package com.sitaram.foodshare.utils.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sitaram.foodshare.theme.black
import com.sitaram.foodshare.theme.cardColor
import com.sitaram.foodshare.theme.lightGray
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.textColor

/**
 * Composable function to display a drop-down menu.
 *
 * @param listOfItems The array of items to display in the drop-down menu.
 * @param selectedItemIndex The index of the currently selected item.
 * @param onClickAction Callback for when an item in the drop-down menu is clicked.
 * expanded Whether the drop-down menu is expanded or not.
 *  onExpandedChange Callback for when the expansion state of the drop-down menu changes.
 *  onDismissRequest Callback for when the drop-down menu is dismissed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    listOfItems: List<String>,
    selectedItemIndex: Int,
    onClickAction: (Int) -> Unit,
    label: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NormalTextView(
                value = listOfItems[selectedItemIndex],
                onValueChange = {},
                readOnly = true,
                textStyle = TextStyle(),
                label = {
                    if (label != null) {
                        TextView(text = label, textType = TextType.INPUT_TEXT_LABEL)
                    }
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .height(54.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = textColor,
                    focusedBorderColor = primary,
                    disabledBorderColor = lightGray,
                    unfocusedBorderColor = lightGray,
                    backgroundColor = cardColor
                ),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOfItems.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = {
                            TextView(
                                text = item,
                                textType = if (index == selectedItemIndex) TextType.BASE_TEXT_BOLD else TextType.BASE_TEXT_REGULAR,
                                color = if (index == selectedItemIndex) primary else textColor
                            )
                        },
                        onClick = {
                            onClickAction(index)
                            expanded = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

// Text field in material 1
@Composable
fun NormalTextView(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    singleLine: Boolean = true,
    maxLines: Int = 0,
    shape: Shape = ShapeDefaults.ExtraSmall,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = black,
        focusedBorderColor = primary,
        disabledBorderColor = lightGray,
        unfocusedBorderColor = lightGray,
        backgroundColor = cardColor
    ),
    textStyle: TextStyle = TextStyle(
        fontWeight = findFontWeight(textType = TextType.INPUT_TEXT_VALUE),
        fontFamily = findFontFamily(textType = TextType.INPUT_TEXT_VALUE),
        fontSize = findTextSize(textType = TextType.INPUT_TEXT_VALUE).sp,
        lineHeight = findLineHeight(textType = TextType.INPUT_TEXT_VALUE).sp,
    ),
) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        maxLines = maxLines,
        shape = shape,
        colors = colors
    )
}