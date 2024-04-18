package com.sitaram.foodshare.utils.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.sitaram.foodshare.theme.gray

/**
 * Painter image
 * use in: painter = rememberAsyncImagePainter(https://image/example.png),
 */
// painter = rememberAsyncImagePainter(
@SuppressLint("ModifierParameter")
@Composable
fun PainterImageView(
    painter: Painter,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = Float.MAX_VALUE,
    colorFilter: ColorFilter? = null
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
    )
}

/**
 * Async image
 * use in: model = https://example.png,
 */
@SuppressLint("ModifierParameter")
@Composable
fun AsyncImageView(
    model: String? = null,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    transform: (AsyncImagePainter.State) -> AsyncImagePainter.State = { it },
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = FilterQuality.High
) {
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        transform = transform,
        onState = onState,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
    )
}

/**
 * AsyncImagePainter image
 * use in: model = url,
 */
@Composable
fun AsyncImagePainter(
    model: Any? = null,
    contentDescription: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
) {
    Image(
        painter = rememberAsyncImagePainter(model = model),
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
    )
}

// This is the image crop to display in circle share
@Composable
fun CircularImageView(
    painter: Painter,
    contentDescription: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        PainterImageView(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(), // Fill the Box with the image
            contentScale = ContentScale.Crop // Adjusts the scaling of the image
        )
    }
}

/**
 * Painter image
 * use in: painterImage = painterResource(id = R.mipmap.img_forgot_password),
 */
@Composable
fun PainterImageView(
    painterImage: Painter,
    contentDescription: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Image(
        painter = painterImage,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

/**
 * Vector icon
 * use in: imageVector = Icons.Default.Email,
 */
@Composable
fun VectorIconView(
    imageVector: ImageVector,
    contentDescription: String? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    tint: Color = gray
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}