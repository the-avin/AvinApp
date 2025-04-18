package com.avin.avinapp.preview.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.avin.avinapp.collector.ComponentRenderCollector
import com.avin.avinapp.data.RenderedComponentInfo
import com.avin.avinapp.data.findTopMostComponentByPosition
import com.avin.avinapp.device.size
import com.avin.avinapp.preview.state.PreviewState
import com.avin.avinapp.utils.calculateScale
import com.avin.avinapp.utils.compose.modifier.handlePointerEvents
import com.avin.avinapp.utils.compose.utils.aspectRatio
import org.jetbrains.jewel.ui.component.CircularProgressIndicator
import org.jetbrains.jewel.ui.util.thenIf

@Composable
fun ComposablePreview(
    state: PreviewState,
    collector: ComponentRenderCollector,
    modifier: Modifier = Modifier
) {
    if (state.currentImage != null) {
        ComposablePreviewImpl(state = state, collector = collector, modifier = modifier)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ComposablePreviewImpl(
    state: PreviewState,
    collector: ComponentRenderCollector,
    modifier: Modifier = Modifier
) {
    val currentDevice = state.currentDevice ?: return
    val imageSize = state.componentSize
    var hoverPosition by remember { mutableStateOf<Offset?>(null) }

    val mappedHoverPosition = remember(hoverPosition, currentDevice) {
        hoverPosition?.let { mapPointerToDevice(it, imageSize, currentDevice.resolution.size) }
    }

    val hoveredComponent by remember(mappedHoverPosition, collector.components) {
        derivedStateOf {
            mappedHoverPosition?.let { pos ->
                collector.components.findTopMostComponentByPosition(pos)
            }
        }
    }

    BoxWithConstraints(modifier = modifier) {
        val width = maxHeight * currentDevice.resolution.size.aspectRatio
        val deviceSize = currentDevice.resolution.size

        Box(
            modifier = Modifier
                .width(width)
                .fillMaxHeight()
                .background(Color.White)
                .handlePointerEvents(
                    onMove = { position -> hoverPosition = position },
                    onExit = { hoverPosition = null }
                )
                .thenIf(hoveredComponent != null) {
                    pointerHoverIcon(PointerIcon.Hand)
                }
                .drawHighlight(
                    component = hoveredComponent,
                    imageSize = imageSize,
                    deviceSize = deviceSize
                ),
            contentAlignment = Alignment.Center
        ) {
            RenderImage(
                bitmap = state.currentImage,
                onSizeChanged = { state.componentSize = it }
            )
            if (state.isRendering) CircularProgressIndicator()
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
            .onGloballyPositioned { onSizeChanged(it.size.toSize()) },
        contentScale = ContentScale.FillHeight
    )
}


private fun Modifier.drawHighlight(
    component: RenderedComponentInfo?,
    imageSize: Size,
    deviceSize: Size
): Modifier = this.drawWithContent {
    drawContent()
    component?.let {
        drawComponentHighlight(it, imageSize, deviceSize)
    }
}

private fun mapPointerToDevice(
    pointer: Offset,
    imageSize: Size,
    deviceSize: Size
): Offset {
    val scale = calculateScale(imageSize, deviceSize)
    return Offset(pointer.x * scale.x, pointer.y * scale.y)
}

private fun DrawScope.drawComponentHighlight(
    component: RenderedComponentInfo,
    imageSize: Size,
    deviceSize: Size,
    color: Color = Color.Red
) {
    // Inverse scale to convert from device coordinates back to image coordinates
    val inverseScale = calculateScale(deviceSize, imageSize)
    val topLeft =
        Offset(component.position.x * inverseScale.x, component.position.y * inverseScale.y)
    val size = Size(component.size.width * inverseScale.x, component.size.height * inverseScale.y)
    drawRect(color, topLeft, size, style = Stroke(2.dp.toPx()))
}