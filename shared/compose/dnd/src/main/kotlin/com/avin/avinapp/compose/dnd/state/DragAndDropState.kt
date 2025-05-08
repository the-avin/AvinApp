package com.avin.avinapp.compose.dnd.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

class DragAndDropState {
    var targetRect: Rect = Rect.Zero
        internal set

    internal var onDropped: ((offset: Offset, data: Any?) -> Unit)? = null
        private set

    fun onDropped(action: (offset: Offset, data: Any?) -> Unit) {
        this.onDropped = action
    }

    internal var onDragEntered: ((offset: Offset, data: Any?) -> Unit)? = null
        private set

    fun onDragEntered(action: (offset: Offset, data: Any?) -> Unit) {
        this.onDragEntered = action
    }
}

@Composable
fun rememberDragAndDropState() = remember { DragAndDropState() }