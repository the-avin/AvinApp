package com.avin.avinapp.preview.collector

import androidx.compose.runtime.CompositionLocal
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.LayoutAwareModifierNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.toSize
import com.avin.avinapp.preview.data.models.RenderedComponentInfo


private data class TrackRenderElement(
    val id: String
) : ModifierNodeElement<TrackRenderNode>() {

    override fun create(): TrackRenderNode = TrackRenderNode(id)

    override fun update(node: TrackRenderNode) {
        node.id = id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun equals(other: Any?): Boolean =
        other is TrackRenderElement && other.id == id

    override fun InspectorInfo.inspectableProperties() {
        name = "TrackRender"
    }
}

private class TrackRenderNode(
    var id: String
) : Modifier.Node(), LayoutModifierNode,
    LayoutAwareModifierNode,
    CompositionLocalConsumerModifierNode {
    override fun onPlaced(coordinates: LayoutCoordinates) {
        val collector = LocalComponentRenderCollector.currentOrNull()
        collector?.updateComponent(
            coordinates.getComponentInfo(id)
        )
    }

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    }

    private fun CompositionLocal<ComponentRenderCollector>.currentOrNull(): ComponentRenderCollector? {
        return runCatching { currentValueOf(this) }.getOrNull()
    }
}

private fun LayoutCoordinates.getComponentInfo(id: String) = RenderedComponentInfo(
    id = id,
    size = this.size.toSize(),
    position = this.localToRoot(Offset.Zero)
)


fun Modifier.trackRender(id: String): Modifier = this.then(
    TrackRenderElement(id)
)