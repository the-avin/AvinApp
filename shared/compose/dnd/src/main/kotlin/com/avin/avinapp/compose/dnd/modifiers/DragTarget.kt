package com.avin.avinapp.compose.dnd.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import com.avin.avinapp.compose.dnd.state.DragAndDropState

fun Modifier.dragTarget(
    state: DragAndDropState,
) = onGloballyPositioned {
    state.targetRect = it.boundsInWindow()
}