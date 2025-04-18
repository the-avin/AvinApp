package com.avin.avinapp.preview.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.avin.avinapp.collector.ComponentRenderCollector
import com.avin.avinapp.data.RenderedComponentInfo
import com.avin.avinapp.device.size
import com.avin.avinapp.preview.state.PreviewState
import com.avin.avinapp.utils.compose.utils.aspectRatio
import org.jetbrains.jewel.ui.component.CircularProgressIndicator
import org.jetbrains.jewel.ui.util.thenIf

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ComposablePreview(
    state: PreviewState,
    collector: ComponentRenderCollector,
    modifier: Modifier = Modifier
) {
    val currentDevice = state.currentDevice ?: return

    val imageSize = state.componentSize
    var hoverPosition by remember { mutableStateOf<Offset?>(null) }

    val mappedHoverPosition = hoverPosition?.let {
        mapPointerToDevice(it, imageSize, currentDevice.resolution.size)
    }

    val hoveredComponent = remember(mappedHoverPosition, collector.components) {
        mappedHoverPosition?.let { pos ->
            collector.components.findLast { Rect(it.position, it.size).contains(pos) }
        }
    }

    BoxWithConstraints(modifier = modifier) {
        val width = maxHeight * currentDevice.resolution.size.aspectRatio
        Box(
            modifier = Modifier
                .width(width)
                .fillMaxHeight()
                .background(Color.White)
                .onPointerEvent(PointerEventType.Move) {
                    it.changes.lastOrNull()?.position?.let { position ->
                        hoverPosition = position
                    }
                }
                .onPointerEvent(PointerEventType.Exit) {
                    hoverPosition = null
                }
                .thenIf(hoveredComponent != null) {
                    pointerHoverIcon(PointerIcon.Hand)
                }
                .drawWithContent {
                    drawContent()
                    hoveredComponent?.let {
                        drawComponentHighlight(it, imageSize, currentDevice.resolution.size)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            RenderImage(state.currentImage, onSizeChanged = { state.componentSize = it })
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

private fun mapPointerToDevice(
    pointer: Offset,
    imageSize: Size,
    deviceSize: Size
): Offset {
    val scaleX = deviceSize.width / imageSize.width.toFloat()
    val scaleY = deviceSize.height / imageSize.height.toFloat()
    return Offset(pointer.x * scaleX, pointer.y * scaleY)
}

private fun DrawScope.drawComponentHighlight(
    component: RenderedComponentInfo,
    imageSize: Size,
    deviceSize: Size
) {
    val scaleX = imageSize.width.toFloat() / deviceSize.width
    val scaleY = imageSize.height.toFloat() / deviceSize.height
    val topLeft = Offset(component.position.x * scaleX, component.position.y * scaleY)
    val size = Size(component.size.width * scaleX, component.size.height * scaleY)
    drawRect(Color.Red, topLeft, size, style = Stroke(2.dp.toPx()))
}