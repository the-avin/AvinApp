package com.avin.avinapp.utils.compose.nodes.drag_handler

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.component.Divider
import org.jetbrains.skiko.Cursor
import org.jetbrains.jewel.ui.Orientation as JewelOrientation

@Composable
fun DragHandler(
    orientation: Orientation,
    onDrag: (Dp) -> Unit,
    modifier: Modifier = Modifier,
    reverseDirection: Boolean = false
) {
    val density = LocalDensity.current
    val reverseDirection =
        (LocalLayoutDirection.current == LayoutDirection.Rtl && orientation == Orientation.Horizontal) || reverseDirection
    Box(
        modifier = modifier
            .then(
                if (orientation == Orientation.Vertical) Modifier.fillMaxWidth()
                    .height(4.dp) else Modifier.fillMaxHeight().width(4.dp)
            )
            .draggable(
                rememberDraggableState { delta ->
                    onDrag.invoke(with(density) { delta.toDp() })
                },
                orientation = orientation,
                reverseDirection = reverseDirection,
            )
            .pointerHoverIcon(
                PointerIcon(
                    Cursor(
                        when (orientation) {
                            Orientation.Vertical -> Cursor.S_RESIZE_CURSOR
                            Orientation.Horizontal -> Cursor.E_RESIZE_CURSOR
                        }
                    )
                ), true
            )
    ) {
        Divider(
            if (orientation == Orientation.Horizontal) JewelOrientation.Vertical else JewelOrientation.Horizontal,
            modifier = if (orientation == Orientation.Vertical) Modifier.fillMaxWidth() else Modifier.fillMaxHeight()
        )
    }
}