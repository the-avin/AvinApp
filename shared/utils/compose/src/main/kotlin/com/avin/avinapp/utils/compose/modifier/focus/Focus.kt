package com.avin.avinapp.utils.compose.modifier.focus

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager

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

@Composable
fun Modifier.clearFocusWhenPressed(action: () -> Unit = {}): Modifier {
    val action by rememberUpdatedState(action)
    val focusManager = LocalFocusManager.current
    return pointerInput(Unit) {
        detectTapGestures {
            focusManager.clearFocus()
            action.invoke()
        }
    }
}