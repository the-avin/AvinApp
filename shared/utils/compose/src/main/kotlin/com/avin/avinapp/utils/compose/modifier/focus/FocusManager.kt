package com.avin.avinapp.utils.compose.modifier.focus

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import com.avin.avinapp.utils.compose.state.focus.Direction

fun Modifier.onVerticalFocusKeyEvent(
    onEvent: (Direction) -> Unit
) = onKeyEvent { event ->
    if (event.type == KeyEventType.KeyDown) {
        when (event.key) {
            Key.DirectionDown -> {
                onEvent.invoke(Direction.Down)
                true
            }

            Key.DirectionUp -> {
                onEvent.invoke(Direction.Up)
                true
            }

            else -> false
        }
    } else false
}

fun Modifier.onHorizontalFocusKeyEvent(
    onEvent: (Direction) -> Unit
) = onKeyEvent { event ->
    if (event.type == KeyEventType.KeyDown) {
        when (event.key) {
            Key.DirectionRight -> {
                onEvent.invoke(Direction.Down)
                true
            }

            Key.DirectionLeft -> {
                onEvent.invoke(Direction.Up)
                true
            }

            else -> false
        }
    } else false
}