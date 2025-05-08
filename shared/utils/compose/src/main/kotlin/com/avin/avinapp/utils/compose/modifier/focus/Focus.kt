package com.avin.avinapp.utils.compose.modifier.focus

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.focusWhenPressed(
    interactionSource: MutableInteractionSource,
    focusRequester: FocusRequester = FocusRequester()
): Modifier {
    return this
        .focusRequester(focusRequester)
        .focusable(interactionSource = interactionSource)
        .onPointerEvent(PointerEventType.Press) {
            focusRequester.requestFocus()
        }
}