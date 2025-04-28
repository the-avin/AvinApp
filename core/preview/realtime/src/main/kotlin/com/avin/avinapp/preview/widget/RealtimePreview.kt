package com.avin.avinapp.preview.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.avin.avinapp.data.models.device.size
import com.avin.avinapp.preview.state.RealtimeRendererState
import com.avin.avinapp.utils.compose.utils.aspectRatio

@OptIn(InternalComposeUiApi::class)
@Composable
fun RealtimePreview(
    state: RealtimeRendererState,
    modifier: Modifier = Modifier
) {
    RealtimePreviewImpl(state, modifier)
}

@OptIn(ExperimentalComposeUiApi::class, InternalComposeUiApi::class)
@Composable
private fun RealtimePreviewImpl(
    state: RealtimeRendererState,
    modifier: Modifier = Modifier
) {
    val currentDevice = state.device

    BoxWithConstraints(modifier = modifier) {
        val width = maxHeight * currentDevice.resolution.size.aspectRatio

        Box(
            modifier = Modifier
                .width(width)
                .background(Color.White)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            state.currentContent?.invoke()
        }
    }
}