package com.avin.avinapp.compose.dnd.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.node.LayoutAwareModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import com.avin.avinapp.compose.dnd.state.DragAndDropState

private class DragTargetModifierNode(
    var state: DragAndDropState,
) : Modifier.Node(), LayoutAwareModifierNode {
    override fun onPlaced(coordinates: LayoutCoordinates) {
        state.targetRect = coordinates.boundsInWindow()
    }
}

private class DragTargetModifierElement(
    private val state: DragAndDropState,
) : ModifierNodeElement<DragTargetModifierNode>() {
    override fun create(): DragTargetModifierNode {
        return DragTargetModifierNode(state)
    }

    override fun equals(other: Any?): Boolean {
        return other is DragTargetModifierElement && other.state == state
    }

    override fun hashCode(): Int {
        return state.hashCode()
    }

    override fun update(node: DragTargetModifierNode) {
        node.state = state
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "DragTarget"
    }
}

fun Modifier.dragTarget(
    state: DragAndDropState,
) = this then DragTargetModifierElement(state)