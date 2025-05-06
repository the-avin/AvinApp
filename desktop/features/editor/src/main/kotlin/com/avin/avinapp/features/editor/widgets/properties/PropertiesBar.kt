package com.avin.avinapp.features.editor.widgets.properties

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.avin.avinapp.data.domain.parameter.ParameterType
import com.avin.avinapp.extensions.isNotNull
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.preview.holder.ComposableStateHolder
import com.avin.avinapp.preview.snapshot.state.SnapshotRenderState
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.theme.styles.panelTitleTextStyle
import com.avin.avinapp.utils.compose.modifier.allPadding
import com.avin.avinapp.utils.compose.modifier.allPaddingValues
import com.avin.avinapp.utils.compose.modifier.verticalPadding
import com.avin.avinapp.utils.compose.nodes.drag_handler.DragHandler
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField
import org.jetbrains.jewel.ui.component.VerticalScrollbar
import org.jetbrains.jewel.ui.component.VerticallyScrollableContainer

private fun coerceWidth(width: Dp) = width.coerceIn(200.dp, 500.dp)

@Composable
fun PropertiesBar(
    renderState: SnapshotRenderState,
) {
    var width by remember { mutableStateOf(300.dp) }
    Row {
        DragHandler(
            orientation = Orientation.Horizontal,
            onDrag = {
                width = coerceWidth(width + it)
            },
            modifier = Modifier.verticalPadding(),
            reverseDirection = true
        )
        if (renderState.selectedComponentId.isNotNull() && renderState.lastHolder.isNotNull()) {
            Box(modifier = Modifier.width(width)) {
                PropertiesBarImpl(renderState)
            }
        } else {
            Column(modifier = Modifier.width(width).fillMaxHeight().allPadding()) {
                Text(
                    dynamicStringRes(Resource.string.properties),
                    style = JewelTheme.panelTitleTextStyle
                )
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No component selected", modifier = Modifier.alpha(.7f))
                }
            }
        }
    }
}

@Composable
private fun PropertiesBarImpl(renderState: SnapshotRenderState) {
    val holder = remember(renderState.selectedComponentId) {
        renderState.lastHolder!!.findHolderById(renderState.selectedComponentId!!)
    }
    if (holder == null) return
    val descriptor = holder.descriptor

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
                        dynamicStringRes(Resource.string.properties),
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
                holder = holder,
                onNewRenderRequest = {
                    renderState.renderPreview()
                }
            )
        }
    }
}


private fun LazyListScope.parametersList(
    holder: ComposableStateHolder,
    onNewRenderRequest: () -> Unit
) {
    val parameters = holder.descriptor.parameters

    items(parameters, key = { it.key }) { parameter ->
        ParameterItem(
            initialValue = holder.parameters[parameter.key],
            parameter = parameter,
            onUpdateValue = {
                holder.updateParameter(parameter.key, it)
                onNewRenderRequest.invoke()
            }
        )
    }
}