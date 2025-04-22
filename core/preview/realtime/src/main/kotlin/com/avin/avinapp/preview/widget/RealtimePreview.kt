package com.avin.avinapp.preview.widget

import androidx.compose.foundation.Image
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.toSize
import com.avin.avinapp.data.models.device.size
import com.avin.avinapp.preview.state.RealtimeRendererState
import com.avin.avinapp.preview.utils.calculateScaledOffset
import com.avin.avinapp.utils.compose.modifier.handlePointerEventsWithType
import com.avin.avinapp.utils.compose.utils.aspectRatio

@OptIn(InternalComposeUiApi::class)
@Composable
fun RealtimePreview(
    state: RealtimeRendererState,
    modifier: Modifier = Modifier
) {
    if (state.currentImage != null) {
        RealtimePreviewImpl(state = state, modifier = modifier)
    }
}

@OptIn(ExperimentalComposeUiApi::class, InternalComposeUiApi::class)
@Composable
private fun RealtimePreviewImpl(
    state: RealtimeRendererState,
    modifier: Modifier = Modifier
) {
    val currentDevice = state.device
    val imageSize = state.componentSize
    val deviceSize = currentDevice.resolution.size

    BoxWithConstraints(modifier = modifier) {
        val width = maxHeight * currentDevice.resolution.size.aspectRatio

        Box(
            modifier = Modifier
                .width(width)
                .background(Color.White)
                .fillMaxHeight()
                .handlePointerEventsWithType { type, offset ->
                    if (type in listOf(PointerEventType.Press, PointerEventType.Release)) {
                        val calculatedOffset = calculateScaledOffset(imageSize, deviceSize, offset)
                        state.sendPointerEvent(type, calculatedOffset)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            RenderImage(
                bitmap = state.currentImage,
                onSizeChanged = { state.componentSize = it }
            )
        }
    }
}


@Composable
private fun RenderImage(bitmap: ImageBitmap?, onSizeChanged: (Size) -> Unit) {
    if (bitmap == null) return
    Image(
        bitmap = bitmap,
        contentDescription = null,
        modifier = Modifier
            .fillMaxHeight()
            .onSizeChanged { onSizeChanged(it.toSize()) },
        contentScale = ContentScale.FillHeight
    )
}