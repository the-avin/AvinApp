package com.avin.avinapp.shortcut.desktop

import com.avin.avinapp.shortcut.platform.PlatformKeyStroke
import com.avin.avinapp.shortcut.platform.ShortcutManager
import java.awt.event.KeyEvent
import javax.swing.KeyStroke

class DesktopShortcutManager : ShortcutManager() {
    override fun getKeyStrokeFromString(key: String): PlatformKeyStroke {
        return KeyStroke.getKeyStroke(key).asPlatform()
    }

    override fun getKeyStrokeFromCode(keyCode: Int): PlatformKeyStroke {
        return KeyStroke.getKeyStroke(keyCode, 0).asPlatform()
    }

    override fun getKeyStrokeFromKeyEvent(event: KeyEvent): PlatformKeyStroke? {
        return runCatching {
            KeyStroke.getKeyStrokeForEvent(event)
        }.getOrNull()?.asPlatform()
    }
}