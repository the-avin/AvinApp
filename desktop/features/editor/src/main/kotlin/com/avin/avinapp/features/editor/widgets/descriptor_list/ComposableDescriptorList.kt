package com.avin.avinapp.features.editor.widgets.descriptor_list

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.avin.avinapp.compose.dnd.state.DragAndDropState
import com.avin.avinapp.data.models.widget.ComposableDescriptor
import com.avin.avinapp.extensions.isNotNull
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.theme.styles.panelTitleTextStyle
import com.avin.avinapp.utils.compose.modifier.allPadding
import com.avin.avinapp.utils.compose.nodes.drag_handler.DragHandler
import com.avin.avinapp.utils.compose.nodes.expandable.ExpandableListColumn
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.Text

private fun coerceWidth(width: Dp) = width.coerceIn(100.dp, 300.dp)

@Composable
fun ComposableDescriptorList(
    descriptors: List<ComposableDescriptor>,
    dragAndDropState: DragAndDropState
) {
    var width by remember { mutableStateOf(250.dp) }
    val groupedDescriptors = remember(descriptors) {
        descriptors.filter { it.group.isNotNull() }.groupBy { it.group!! }
    }
    val groupedDescriptorsWithAll = buildMap {
        put(dynamicStringRes(Resource.string.all), descriptors)
        putAll(groupedDescriptors)
    }
    Row(
        modifier = Modifier.zIndex(10f)
    ) {
        ExpandableListColumn(
            items = groupedDescriptorsWithAll,
            modifier = Modifier
                .fillMaxHeight()
                .allPadding()
                .width(width),
            topContent = {
                Text(
                    dynamicStringRes(Resource.string.components),
                    style = JewelTheme.panelTitleTextStyle
                )
                Spacer(Modifier.height(8.dp))
            },
        ) { descriptor ->
            ComposableDescriptorItem(
                descriptor,
                dragAndDropState,
            )
        }
    }
    DragHandler(
        orientation = Orientation.Horizontal,
        onDrag = {
            width = coerceWidth(width + it)
        },
    )
}