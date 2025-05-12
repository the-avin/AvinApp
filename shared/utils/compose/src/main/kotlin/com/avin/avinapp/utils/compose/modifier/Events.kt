package com.avin.avinapp.utils.compose.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.handlePointerEvents(
    onMove: ((Offset) -> Unit)? = null,
    onClick: ((Offset) -> Unit)? = null,
    onExit: ((Offset) -> Unit)? = null,
    onEnter: ((Offset) -> Unit)? = null,
    onRelease: ((Offset) -> Unit)? = null,
): Modifier = this
    .pointerInput(onMove, onClick, onExit, onEnter, onRelease) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                val position = event.changes.lastOrNull()?.position
                val change = event.changes.lastOrNull()

                when (event.type) {
                    PointerEventType.Move -> position?.let {
                        onMove?.invoke(it)
                    }

                    PointerEventType.Press -> position?.let {
                        onClick?.invoke(it)
                    }

                    PointerEventType.Exit -> position?.let {
                        onExit?.invoke(it)
                    }

                    PointerEventType.Release -> position?.let {
                        onRelease?.invoke(it)
                    }

                    PointerEventType.Enter -> position?.let {
                        onEnter?.invoke(it)
                    }

                    else -> Unit
                }
                change?.consume()
            }
        }
    }

fun Modifier.handlePointerEventsWithType(
    handle: (PointerEventType, Offset) -> Unit
): Modifier = this
    .pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                val position = event.changes.lastOrNull()?.position
                handle.invoke(event.type, position ?: continue)
                event.changes.lastOrNull()?.consume()
            }
        }
    }