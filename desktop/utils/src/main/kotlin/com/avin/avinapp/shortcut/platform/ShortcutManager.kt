package com.avin.avinapp.shortcut.platform

import com.avin.avinapp.platform.Platform
import com.avin.avinapp.shortcut.KeyboardKeys
import java.awt.event.KeyEvent

typealias KeyAction = () -> Unit

abstract class ShortcutManager {
    private val _shortcuts = mutableMapOf<PlatformKeyStroke, KeyAction>()
    val shortcuts = _shortcuts.toMap()

    fun register(keyStroke: PlatformKeyStroke, action: KeyAction) {
        _shortcuts[keyStroke] = action
    }

    abstract fun getKeyStrokeFromString(key: String): PlatformKeyStroke
    abstract fun getKeyStrokeFromCode(keyCode: Int): PlatformKeyStroke
    abstract fun getKeyStrokeFromKeyEvent(event: KeyEvent): PlatformKeyStroke?

    infix fun String.to(action: KeyAction) = register(getKeyStrokeFromString(this), action)
    infix fun Int.to(action: KeyAction) = register(getKeyStrokeFromCode(this), action)
    infix fun PlatformKeyStroke.to(action: KeyAction) = register(this, action)

    fun handle(event: KeyEvent): Boolean {
        val stroke = getKeyStrokeFromKeyEvent(event) ?: return false
        return _shortcuts[stroke]?.invoke() != null
    }

    fun getMetaKey(): PlatformKeyStroke {
        val key = if (Platform.isMac()) KeyboardKeys.META_KEY else KeyboardKeys.CTRL_KEY
        return getKeyStrokeFromString(key)
    }

    fun getDeleteKey(): PlatformKeyStroke {
        return if (Platform.isMac())
            getKeyStrokeFromCode(KeyboardKeys.BACKSPACE_KEY)
        else getKeyStrokeFromString(KeyboardKeys.DELETE_KEY)
    }
}