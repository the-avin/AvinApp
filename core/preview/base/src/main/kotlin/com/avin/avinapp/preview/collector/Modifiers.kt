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
    val id: String,
    val descriptorKey: String
) : ModifierNodeElement<TrackRenderNode>() {

    override fun create(): TrackRenderNode = TrackRenderNode(id, descriptorKey)

    override fun update(node: TrackRenderNode) {
        node.id = id
        node.descriptorKey = descriptorKey
    }

    override fun hashCode(): Int = id.hashCode()

    override fun equals(other: Any?): Boolean =
        other is TrackRenderElement && other.id == id && other.descriptorKey == descriptorKey

    override fun InspectorInfo.inspectableProperties() {
        name = "TrackRender"
    }
}

private class TrackRenderNode(
    var id: String,
    var descriptorKey: String
) : Modifier.Node(),
    LayoutAwareModifierNode,
    CompositionLocalConsumerModifierNode {
    override fun onPlaced(coordinates: LayoutCoordinates) {
        val collector = LocalComponentRenderCollector.currentOrNull()
        collector?.updateComponent(
            coordinates.getComponentInfo(id, descriptorKey)
        )
    }

    private fun CompositionLocal<ComponentRenderCollector>.currentOrNull(): ComponentRenderCollector? {
        return runCatching { currentValueOf(this) }.getOrNull()
    }
}

private fun LayoutCoordinates.getComponentInfo(id: String, descriptorKey: String) =
    RenderedComponentInfo(
        id = id,
        descriptorKey = descriptorKey,
        size = this.size.toSize(),
        position = this.localToRoot(Offset.Zero)
    )


fun Modifier.trackRender(id: String, descriptorKey: String): Modifier = this.then(
    TrackRenderElement(id, descriptorKey)
)