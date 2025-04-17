package com.avin.avinapp.collector

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.toSize
import com.avin.avinapp.data.RenderedComponentInfo

fun Modifier.trackRender(id: String): Modifier = composed {
    val collector = LocalComponentRenderCollector.current
    onGloballyPositioned { layoutCoordinates ->
        collector.updateComponent(
            layoutCoordinates.getComponentInfo(id)
        )
    }
}


private fun LayoutCoordinates.getComponentInfo(id: String) = RenderedComponentInfo(
    id = id,
    size = this.size.toSize(),
    position = this.localToRoot(Offset.Zero)
)