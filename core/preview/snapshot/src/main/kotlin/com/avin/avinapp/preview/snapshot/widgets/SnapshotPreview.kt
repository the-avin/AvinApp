package com.avin.avinapp.preview.snapshot.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.toSize
import com.avin.avinapp.compose.dnd.state.DragAndDropState
import com.avin.avinapp.data.models.device.size
import com.avin.avinapp.data.models.widget.ComposableDescriptor
import com.avin.avinapp.extensions.isNotNull
import com.avin.avinapp.preview.collector.ComponentRenderCollector
import com.avin.avinapp.preview.data.models.RenderedComponentInfo
import com.avin.avinapp.preview.data.models.findComponentById
import com.avin.avinapp.preview.data.models.findTopMostComponentByPosition
import com.avin.avinapp.preview.data.models.findTopmostParentComponentByPosition
import com.avin.avinapp.preview.snapshot.state.SnapshotRenderState
import com.avin.avinapp.preview.snapshot.utils.drawComponentGuidesWithDistances
import com.avin.avinapp.preview.snapshot.utils.drawComponentHighlight
import com.avin.avinapp.preview.snapshot.utils.drawComponentHighlightInfo
import com.avin.avinapp.preview.snapshot.utils.mapPointerToDevice
import com.avin.avinapp.utils.compose.modifier.handlePointerEvents
import com.avin.avinapp.utils.compose.utils.aspectRatio
import org.jetbrains.jewel.ui.component.CircularProgressIndicator
import org.jetbrains.jewel.ui.util.thenIf

@Composable
fun SnapshotPreview(
    state: SnapshotRenderState,
    collector: ComponentRenderCollector,
    dragAndDropState: DragAndDropState,
    modifier: Modifier = Modifier
) {
    if (state.currentImage != null) {
        SnapshotPreviewImpl(
            state = state,
            collector = collector,
            dragAndDropState = dragAndDropState,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SnapshotPreviewImpl(
    state: SnapshotRenderState,
    collector: ComponentRenderCollector,
    dragAndDropState: DragAndDropState,
    modifier: Modifier = Modifier
) {
    val currentDevice = state.currentDevice ?: return
    val imageSize by rememberUpdatedState(state.componentSize)
    val deviceSize by rememberUpdatedState(currentDevice.resolution.size)
    val aspectRatio = deviceSize.aspectRatio

    var hoverPosition by remember { mutableStateOf<Offset?>(null) }
    val selectedComponentId = state.selectedComponentId
    val textMeasurer = rememberTextMeasurer()

    val mappedHoverPosition = remember(hoverPosition, currentDevice) {
        hoverPosition?.let { mapPointerToDevice(it, imageSize, deviceSize) }
    }

    val hoveredComponent by remember(mappedHoverPosition, collector.components) {
        derivedStateOf {
            mappedHoverPosition?.let { collector.components.findTopMostComponentByPosition(it) }
        }
    }

    val components = collector.components

    val selectedComponent = remember(selectedComponentId, components) {
        val id = selectedComponentId
        id?.let { components.findComponentById(it) }
    }

    var draggedComponent by remember {
        mutableStateOf<RenderedComponentInfo?>(null)
    }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        runCatching { focusRequester.requestFocus() }
        dragAndDropState.apply {
            onDragEnteredWithType<ComposableDescriptor> { offset, data ->
                val mappedPosition = mapPointerToDevice(offset, imageSize, deviceSize)
                draggedComponent = collector.components
                    .findTopmostParentComponentByPosition(mappedPosition)
            }
            onExit { draggedComponent = null }
        }
    }

    BoxWithConstraints(modifier = modifier) {
        val width = maxHeight * aspectRatio

        Box(
            modifier = Modifier
                .width(width)
                .fillMaxHeight()
                .focusRequester(focusRequester)
                .focusable()
                .onEscapeKeyPressed(state::clearSelectedComponents)
                .handlePointerEvents(
                    onMove = { hoverPosition = it },
                    onExit = { hoverPosition = null },
                    onClick = { clickPos ->
                        val mapped = mapPointerToDevice(clickPos, imageSize, deviceSize)
                        collector.components.findTopMostComponentByPosition(mapped)
                            ?.let(state::selectComponent) ?: state.clearSelectedComponents()
                    }
                )
                .thenIf(hoveredComponent.isNotNull()) {
                    pointerHoverIcon(PointerIcon.Hand)
                }
                .drawHighlight(hoveredComponent, imageSize, deviceSize, textMeasurer)
                .drawHighlight(selectedComponent, imageSize, deviceSize)
                .drawHighlight(draggedComponent, imageSize, deviceSize),
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
    deviceSize: Size,
    textMeasurer: TextMeasurer? = null,
    color: Color = Color.Red
): Modifier = this.drawWithContent {
    drawContent()
    component?.let { componentInfo ->
        textMeasurer?.let {
            drawComponentGuidesWithDistances(
                componentInfo,
                imageSize,
                deviceSize,
                it,
                color = color
            )
            drawComponentHighlightInfo(componentInfo, imageSize, deviceSize, it, color = color)
        }
        drawComponentHighlight(componentInfo, imageSize, deviceSize, color = color)
    }
}


private fun Modifier.onEscapeKeyPressed(action: () -> Unit) = onPreviewKeyEvent { event ->
    if (event.key.keyCode == Key.Escape.keyCode) {
        action.invoke()
        return@onPreviewKeyEvent true
    }
    false
}