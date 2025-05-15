package com.avin.avinapp.utils.compose.nodes.resizable

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.avin.avinapp.utils.compose.nodes.drag_handler.DragHandler
import com.avin.avinapp.utils.compose.state.size_handler.rememberResizableSize

@Composable
fun ResizableStartPanel(
    initialSize: Dp = 300.dp,
    sizeRange: ClosedRange<Dp>? = 200.dp..500.dp,
    content: @Composable () -> Unit
) {
    Row {
        val width by rememberResizableSize(
            initialSize = initialSize,
            sizeRange = sizeRange
        ) {
            DragHandler(
                orientation = Orientation.Horizontal,
                onDrag = it,
                reverseDirection = true
            )
        }
        Box(Modifier.width(width)) { content.invoke() }
    }
}