package com.avin.avinapp.preview.realtime.widget

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
import com.avin.avinapp.preview.realtime.state.RealtimeRendererState
import com.avin.avinapp.preview.realtime.utils.calculateFinalDensity
import com.avin.avinapp.preview.realtime.utils.calculateScaledWidth
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
        val frameHeightPx = constraints.maxHeight.toFloat()
        val deviceResolution = currentDevice.resolution

        val finalDensity = calculateFinalDensity(
            initialDensity = initialDensity,
            frameHeightPx = frameHeightPx,
            deviceHeight = deviceResolution.height
        )

        val calculatedWidth = calculateScaledWidth(
            frameHeightPx = frameHeightPx,
            aspectRatio = deviceResolution.size.aspectRatio
        )

        ProvidePreviewDensity(finalDensity, calculatedWidth, modifier) {
            state.currentContent?.invoke()
        }
    }
}

@Composable
private fun ProvidePreviewDensity(
    density: Float,
    widthPx: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalDensity provides Density(density)) {
        Box(
            modifier = Modifier
                .width(with(LocalDensity.current) { widthPx.toDp() })
                .fillMaxHeight()
                .background(Color.White)
                .then(modifier),
            contentAlignment = Alignment.Center
        ) {
            content.invoke()
        }
    }
}