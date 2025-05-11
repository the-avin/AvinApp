package com.avin.avinapp.shortcut.desktop

import com.avin.avinapp.shortcut.platform.PlatformKeyStroke
import java.awt.event.KeyEvent
import javax.swing.KeyStroke

data class DesktopKeyStroke(val keyStroke: KeyStroke) : PlatformKeyStroke {
    override fun getText(): String {
        return KeyEvent.getKeyText(keyStroke.keyCode)
    }
}

fun KeyStroke.asPlatform() = DesktopKeyStroke(this)