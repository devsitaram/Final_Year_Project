package com.sitaram.foodshare.utils.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sitaram.foodshare.R
import com.sitaram.foodshare.theme.primary
import com.sitaram.foodshare.theme.white

// Circle processing indicator
@Composable
fun ProgressIndicatorView(
    modifier: Modifier = Modifier,
    color: Color = primary,
    backgroundColor: Color = white,
) {
    DividerView()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(white),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier
                .wrapContentSize()
                .align(Alignment.Center),
            color = color,
            backgroundColor = backgroundColor
        )
    }
}

// Custom processing dialog box
@Composable
fun ProcessingDialogView(
    title: String? = null,
    body: String? = null,
    onDismissRequest: (() -> Unit)? = null,
) {
    Dialog(
        onDismissRequest = { onDismissRequest?.invoke() },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxWidth()
                .background(white, shape = RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(8.dp),
                    color = primary
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    TextView(
                        text = title ?: stringResource(R.string.processing),
                        textType = TextType.TITLE4
                    )
                    TextView(
                        text = body ?: stringResource(R.string.please_wait_for_processing),
                        textType = TextType.BASE_TEXT_REGULAR,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

// Pull to refresh indicator
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshIndicatorView(
    modifier: Modifier,
    refreshing: Boolean,
    state: PullRefreshState,
    backgroundColor: Color = white,
    contentColor: Color = primary,
    scale: Boolean = false,
) {
    PullRefreshIndicator(
        refreshing = refreshing,
        state = state,
        modifier = modifier,
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        scale = scale
    )
}