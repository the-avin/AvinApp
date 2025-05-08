package com.avin.avinapp.compose.dnd.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

@Immutable
class DragAndDropState {
    var targetRect: Rect = Rect.Zero
        internal set

    internal var onDropped: ((offset: Offset, data: Any?) -> Unit)? = null
        private set

    fun onDropped(action: (offset: Offset, data: Any?) -> Unit) {
        this.onDropped = action
    }

    inline fun <reified T : Any?> onDroppedWithType(crossinline action: (offset: Offset, data: T) -> Unit) {
        onDropped { offset, data ->
            if (data is T) {
                action.invoke(offset, data)
            }
        }
    }

    internal var onDragEntered: ((offset: Offset, data: Any?) -> Unit)? = null
        private set

    fun onDragEntered(action: (offset: Offset, data: Any?) -> Unit) {
        this.onDragEntered = action
    }

    inline fun <reified T : Any?> onDragEnteredWithType(crossinline action: (offset: Offset, data: T) -> Unit) {
        onDragEntered { offset, data ->
            if (data is T) {
                action.invoke(offset, data)
            }
        }
    }

    internal var onExit: (() -> Unit)? = null
        private set

    fun onExit(action: () -> Unit) {
        this.onExit = action
    }
}

@Composable
fun rememberDragAndDropState() = remember { DragAndDropState() }