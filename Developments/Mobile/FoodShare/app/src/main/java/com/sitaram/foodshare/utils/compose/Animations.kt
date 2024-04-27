package com.sitaram.foodshare.utils.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionResult
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

/**
 * Composable function that displays a Lottie animation.
 * This function loads a Lottie animation from the provided raw resource ID,
 * and allows controlling its playback and speed.
 *
 * @param rawResource The resource ID of the Lottie animation.
 * @param isAnimating Whether the animation should be playing.
 * @param speed The playback speed of the animation.
 * @param stopAtProgress The progress value at which the animation should stop.
 * @param modifier The modifier for the Lottie animation.
 */
@Composable
fun LottieAnimationsView(
    rawResource: Int,
    isAnimating: Boolean = false,
    speed: Float = 1f,
    stopAtProgress: Float = 1f,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {

    var isAnimation by remember { mutableStateOf(isAnimating) }

    val compositionResult: LottieCompositionResult =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(rawResource))
    val progress by animateLottieCompositionAsState(
        composition = compositionResult.value,
        isPlaying = isAnimation,
        iterations = LottieConstants.IterateForever,
        speed = speed
    )
    LottieAnimation(
        composition = compositionResult.value,
        progress = progress,
        modifier = modifier
    )

    LaunchedEffect(key1 = compositionResult, key2 = progress) {
        if (progress >= stopAtProgress) {
            isAnimation = false
        }
    }
}