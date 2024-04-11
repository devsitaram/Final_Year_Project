package com.sitaram.foodshare.utils.compose

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.translate

@Composable
fun CanvasView(
    modifier: Modifier = Modifier,
    left: Float = 0f,
    top: Float = 0f,
    color: Color = Color.Unspecified,
    radius: Float = 0f,
    center: Offset = Offset(0f, 0f), // Default center at (0, 0)
    alpha: Float = 1f, // Default alpha fully opaque
    style: DrawStyle = Fill, // Default draw style as Fill
    colorFilter: ColorFilter? = null, // Default no color filter
    blendMode: BlendMode = BlendMode.SrcOver // Default blend mode
) {
    Canvas(modifier = modifier) {
        translate(left = left, top = top) {
            drawCircle(
                color = color,
                radius = radius,
                center = center,
                alpha = alpha,
                style = style,
                colorFilter = colorFilter,
                blendMode = blendMode
            )
        }
    }
}