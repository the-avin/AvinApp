package com.avin.avinapp.utils.compose.nodes.resizable

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.avin.avinapp.utils.compose.nodes.drag_handler.DragHandler

@Composable
fun ResizableEndPanel(
    initialSize: Dp = 300.dp,
    sizeRange: ClosedRange<Dp> = 200.dp..500.dp,
    content: @Composable () -> Unit
) {
    var width by remember {
        mutableStateOf(initialSize)
    }
    Row {
        Box(Modifier.width(width)) { content.invoke() }

        DragHandler(
            orientation = Orientation.Horizontal,
            onDrag = {
                width = (width + it).coerceIn(sizeRange)
            },
        )
    }
}