package com.avin.avinapp.shortcut

import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.awtEventOrNull
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import com.avin.avinapp.shortcut.platform.ShortcutManager

fun Modifier.handleShortcutManager(shortcutManager: ShortcutManager) = onKeyEvent { event ->
    if (event.type == KeyEventType.KeyDown) {
        shortcutManager.handle(event.awtEventOrNull ?: return@onKeyEvent false)
    } else false
}