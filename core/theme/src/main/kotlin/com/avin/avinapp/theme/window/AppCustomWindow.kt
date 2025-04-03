package com.avin.avinapp.theme.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.theme.compositions.LocalWindow
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.painterResource
import org.jetbrains.jewel.window.DecoratedWindow
import org.jetbrains.jewel.window.DecoratedWindowScope
import org.jetbrains.jewel.window.styling.DecoratedWindowStyle
import org.jetbrains.jewel.window.styling.LocalDecoratedWindowStyle
import java.awt.Color

@Composable
fun AppCustomWindow(
    onCloseRequest: () -> Unit,
    state: WindowState = rememberWindowState(),
    visible: Boolean = true,
    title: String = "",
    icon: Painter? = painterResource(Resource.image.LOGO),
    resizable: Boolean = true,
    enabled: Boolean = true,
    focusable: Boolean = true,
    alwaysOnTop: Boolean = false,
    onPreviewKeyEvent: (KeyEvent) -> Boolean = { false },
    onKeyEvent: (KeyEvent) -> Boolean = {
        if (it.key.keyCode == Key.W.keyCode && (it.isMetaPressed || it.isCtrlPressed) && it.type == KeyEventType.KeyUp) {
            onCloseRequest.invoke()
            true
        } else {
            false
        }
    },
    style: DecoratedWindowStyle = LocalDecoratedWindowStyle.current,
    content: @Composable DecoratedWindowScope.() -> Unit,
) {
    DecoratedWindow(
        onCloseRequest = onCloseRequest,
        state = state,
        visible = visible,
        title = title,
        icon = icon,
        resizable = resizable,
        enabled = enabled,
        focusable = focusable,
        alwaysOnTop = alwaysOnTop,
        onPreviewKeyEvent = onPreviewKeyEvent,
        onKeyEvent = onKeyEvent,
        style = style,
    ) {
        ApplyWindowColors()
        CompositionLocalProvider(LocalWindow provides window) {
            content.invoke(this)
        }
    }
}


@Composable
fun DecoratedWindowScope.ApplyWindowColors() {
    val panelColor = JewelTheme.globalColors.panelBackground
    SideEffect {
        panelColor.run {
            window.background = Color(red, green, blue, alpha)
        }
    }
}