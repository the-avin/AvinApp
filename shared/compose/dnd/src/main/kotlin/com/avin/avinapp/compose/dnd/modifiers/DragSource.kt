package com.avin.avinapp.compose.dnd.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.node.LayoutAwareModifierNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.PointerInputModifierNode
import androidx.compose.ui.node.invalidatePlacement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.round
import com.avin.avinapp.compose.dnd.state.DragAndDropState

private class DragSourceModifierNode(
    var state: DragAndDropState,
    var data: Any?
) : Modifier.Node(), LayoutModifierNode, LayoutAwareModifierNode, PointerInputModifierNode {
    private var isDragging = false
    private val zIndex: Float
        get() = if (isDragging) 100f else 0f

    private var componentRect = Rect.Zero


    private var offset = Offset.Zero
    private var initialPressPosition = Offset.Zero

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            placeable.place(offset.round(), zIndex)
        }
    }

    override fun onPlaced(coordinates: LayoutCoordinates) {
        if (!isDragging) {
            componentRect = coordinates.boundsInWindow()
        }
    }

    private fun cancelPointerEvent() {
        isDragging = false
        state.onExit?.invoke()
        if (offset != Offset.Zero) {
            offset = Offset.Zero
            invalidatePlacement()
        }
    }

    override fun onCancelPointerInput() {
        cancelPointerEvent()
    }

    override fun onPointerEvent(
        pointerEvent: PointerEvent,
        pass: PointerEventPass,
        bounds: IntSize
    ) {
        if (pass != PointerEventPass.Main) return

        pointerEvent.changes.firstOrNull()?.let { change ->
            when {
                change.changedToDown() -> {
                    initialPressPosition = change.position
                    isDragging = true
                }

                change.changedToUp() -> {
                    isDragging = false
                    handleDragUpdate()
                    cancelPointerEvent()
                }

                change.pressed && change.positionChanged() -> {
                    offset += change.positionChange()
                    change.consume()
                    handleDragUpdate()
                    invalidatePlacement()
                }
            }
        }
    }

    private fun handleDragUpdate() {
        val dragOffset = componentRect.topLeft + offset + initialPressPosition
        val isInsideTargetArea = state.targetRect.contains(dragOffset)
        if (isInsideTargetArea) {
            val deltaOffset = dragOffset - state.targetRect.topLeft

            if (isDragging) {
                state.onDragEntered?.invoke(deltaOffset, data)
            } else {
                state.onDropped?.invoke(deltaOffset, data)
            }
        }
    }
}


private class DragSourceModifierElement(
    private val state: DragAndDropState,
    private val data: Any?
) : ModifierNodeElement<DragSourceModifierNode>() {
    override fun create(): DragSourceModifierNode {
        return DragSourceModifierNode(state, data)
    }

    override fun equals(other: Any?): Boolean {
        return other is DragSourceModifierElement && other.state == state && other.data == data
    }

    override fun hashCode(): Int {
        return state.hashCode()
    }

    override fun update(node: DragSourceModifierNode) {
        node.state = state
        node.data = data
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "DragSource"
    }
}

fun Modifier.dragSource(
    state: DragAndDropState,
    data: Any?
) = this then DragSourceModifierElement(state, data)