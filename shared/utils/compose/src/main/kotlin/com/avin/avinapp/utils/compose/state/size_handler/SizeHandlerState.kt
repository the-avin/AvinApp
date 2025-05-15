package com.avin.avinapp.utils.compose.state.size_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberResizableSize(
    initialSize: Dp = 300.dp,
    sizeRange: ClosedRange<Dp>? = 200.dp..500.dp,
    dragHandle: @Composable (onDrag: (Dp) -> Unit) -> Unit
): State<Dp> {
    var size by remember { mutableStateOf(initialSize) }

    fun coerceSize(size: Dp) = sizeRange?.let { size.coerceIn(it) } ?: size

    dragHandle { delta ->
        size = coerceSize(size + delta)
    }

    return derivedStateOf { size }
}