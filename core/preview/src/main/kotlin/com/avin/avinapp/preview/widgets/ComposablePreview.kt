package com.avin.avinapp.preview.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.avin.avinapp.collector.ComponentRenderCollector
import com.avin.avinapp.data.RenderedComponentInfo
import com.avin.avinapp.preview.state.PreviewState
import org.jetbrains.jewel.ui.component.CircularProgressIndicator

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ComposablePreview(
    state: PreviewState,
    collector: ComponentRenderCollector,
    modifier: Modifier = Modifier
) {
    val currentDevice = state.currentDevice ?: return

    var hoverPosition by remember { mutableStateOf<Offset?>(null) }
    var hoveredComponent by remember { mutableStateOf<RenderedComponentInfo?>(null) }

    if (state.currentImage != null) {
        var imageSize by remember { mutableStateOf(IntSize.Zero) }

        Box(
            modifier = modifier
                .background(Color.White)
                .pointerMoveFilter(
                    onMove = {
                        hoverPosition = it
                        true
                    },
                    onExit = {
                        hoverPosition = null
                        hoveredComponent = null
                        false
                    }
                )
                .drawWithContent {
                    drawContent()
                    hoveredComponent?.let {
                        val scaleX = imageSize.width.toFloat() / currentDevice.resolution.width
                        val scaleY = imageSize.height.toFloat() / currentDevice.resolution.height
                        val topLeft = Offset(it.position.x * scaleX, it.position.y * scaleY)
                        val size = Size(it.size.width * scaleX, it.size.height * scaleY)
                        drawRect(Color.Red, topLeft, size, style = Stroke(2.dp.toPx()))
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                bitmap = state.currentImage!!,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .onGloballyPositioned {
                        imageSize = it.size
                    },
                contentScale = ContentScale.FillHeight
            )
            if (state.isRendering) CircularProgressIndicator()
        }

        LaunchedEffect(hoverPosition, imageSize, collector.components) {
            val pos = hoverPosition ?: return@LaunchedEffect
            val scaleX = currentDevice.resolution.width / imageSize.width.toFloat()
            val scaleY = currentDevice.resolution.height / imageSize.height.toFloat()
            val mappedPos = Offset(pos.x * scaleX, pos.y * scaleY)
            hoveredComponent = collector.components.findLast {
                Rect(it.position, it.size).contains(mappedPos)
            }
        }
    }
}