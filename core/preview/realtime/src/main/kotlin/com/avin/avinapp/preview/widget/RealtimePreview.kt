package com.avin.avinapp.preview.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
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

@Composable
private fun RealtimePreviewImpl(
    state: RealtimeRendererState,
    modifier: Modifier = Modifier
) {
    val currentDevice = state.currentDevice
    val initialDensity = currentDevice.density

    BoxWithConstraints(modifier = modifier) {
        val frameHeightPx = with(LocalDensity.current) { maxHeight.toPx() }

        val deviceResolution = currentDevice.resolution
        val deviceAspectRatio = deviceResolution.size.aspectRatio

        val calculatedDensity = deviceResolution.height / frameHeightPx

        val finalDensity = initialDensity * (calculatedDensity / initialDensity)

        val calculatedWidth = frameHeightPx * deviceAspectRatio

        CompositionLocalProvider(
            LocalDensity provides Density(finalDensity)
        ) {
            Box(
                modifier = Modifier
                    .width(with(LocalDensity.current) { calculatedWidth.toDp() })
                    .fillMaxHeight()
                    .background(Color.White)
                    .then(modifier),
                contentAlignment = Alignment.Center
            ) {
                state.currentContent?.invoke()
            }
        }
    }
}