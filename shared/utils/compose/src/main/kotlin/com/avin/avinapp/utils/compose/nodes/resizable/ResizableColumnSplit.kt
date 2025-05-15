package com.avin.avinapp.utils.compose.nodes.resizable

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.avin.avinapp.utils.compose.nodes.drag_handler.DragHandler

@Composable
fun ResizableColumnSplit(
    topContent: (@Composable () -> Unit)?,
    bottomContent: (@Composable () -> Unit)?,
    modifier: Modifier = Modifier
) {
    if (topContent == null && bottomContent == null) return

    BoxWithConstraints(modifier = modifier) {
        var topSize by remember { mutableStateOf(maxHeight / 2f) }

        Column(Modifier.fillMaxHeight()) {
            if (topContent != null) {
                Box(
                    Modifier.height(if (bottomContent != null) topSize else Dp.Unspecified)
                ) {
                    topContent()
                }
            }

            if (topContent != null && bottomContent != null) {
                DragHandler(
                    orientation = Orientation.Vertical,
                    onDrag = {
                        topSize += it
                    },
                )
            }

            if (bottomContent != null) {
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    bottomContent()
                }
            }
        }
    }
}