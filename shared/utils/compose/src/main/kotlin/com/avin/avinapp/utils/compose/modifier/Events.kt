package com.avin.avinapp.utils.compose.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.handlePointerEvents(
    onMove: ((Offset) -> Unit)? = null,
    onClick: ((Offset) -> Unit)? = null,
    onExit: (() -> Unit)? = null
): Modifier = this
    .pointerInput(onMove, onClick, onExit) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                val position = event.changes.lastOrNull()?.position

                when (event.type) {
                    PointerEventType.Move -> position?.let { onMove?.invoke(it) }
                    PointerEventType.Press -> position?.let { onClick?.invoke(it) }
                    PointerEventType.Exit -> onExit?.invoke()
                    else -> Unit
                }
            }
        }
    }