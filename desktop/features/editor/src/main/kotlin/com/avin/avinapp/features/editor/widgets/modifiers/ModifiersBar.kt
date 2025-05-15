package com.avin.avinapp.features.editor.widgets.modifiers

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.avin.avinapp.data.models.descriptor.modifier.ModifierDescriptor
import com.avin.avinapp.extensions.isNotNull
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.preview.holder.ComposableStateHolder
import com.avin.avinapp.preview.snapshot.state.SnapshotRenderState
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.theme.styles.panelTitleTextStyle
import com.avin.avinapp.utils.compose.modifier.allPadding
import com.avin.avinapp.utils.compose.modifier.allPaddingValues
import com.avin.avinapp.utils.compose.nodes.drag_handler.DragHandler
import com.avin.avinapp.utils.compose.state.size_handler.rememberResizableSize
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.Divider
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.VerticallyScrollableContainer

@Composable
fun ModifiersBar(
    renderState: SnapshotRenderState,
    modifiers: List<ModifierDescriptor>
) {

    if (renderState.selectedComponentId.isNotNull() && renderState.lastHolder.isNotNull()) {
        ModifiersBarImpl(renderState, modifiers)
    } else {
        Column(modifier = Modifier.fillMaxSize().allPadding()) {
            Text(
                dynamicStringRes(Resource.string.modifiers),
                style = JewelTheme.panelTitleTextStyle
            )
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No component selected", modifier = Modifier.alpha(.7f))
            }
        }
    }
}

@Composable
fun ModifiersBarImpl(renderState: SnapshotRenderState, modifiers: List<ModifierDescriptor>) {
    val holder = remember(renderState.selectedComponentId) {
        renderState.lastHolder!!.findHolderById(renderState.selectedComponentId!!)
    }
    if (holder == null) return
    val descriptor = holder.descriptor

    val holderModifiers = remember(holder) {
        modifiers.filter { holder.modifiers.contains(it.descriptorKey) }
    }

    val lazyListState = rememberLazyListState()

    VerticallyScrollableContainer(scrollState = lazyListState) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = allPaddingValues,
            state = lazyListState
        ) {
            item("title_bar") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        dynamicStringRes(Resource.string.modifiers),
                        style = JewelTheme.panelTitleTextStyle
                    )
                    Text(
                        " - ${descriptor.name}",
                        modifier = Modifier.alpha(.7f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(Modifier)
            }
            parametersList(
                modifiers = holderModifiers,
                holder = holder,
                onNewRenderRequest = {
                    renderState.renderPreview()
                }
            )
        }
    }
}


private fun LazyListScope.parametersList(
    modifiers: List<ModifierDescriptor>,
    holder: ComposableStateHolder,
    onNewRenderRequest: () -> Unit,
) {
    itemsIndexed(modifiers, key = { _, item -> item.descriptorKey }) { index, modifier ->
        ModifierItem(
            modifier = modifier,
            values = holder.modifiers[modifier.descriptorKey]!!,
            onNewRenderRequest = onNewRenderRequest
        )
        if (index == modifiers.size - 1) {
            Divider(
                org.jetbrains.jewel.ui.Orientation.Horizontal,
                modifier = Modifier.padding(top = 8.dp).fillMaxWidth()
            )
        }
    }
}