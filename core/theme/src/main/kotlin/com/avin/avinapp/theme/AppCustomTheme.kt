package com.avin.avinapp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.avin.avinapp.theme.data.LocalCurrentTheme
import com.avin.avinapp.theme.data.Theme
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.intui.standalone.theme.darkThemeDefinition
import org.jetbrains.jewel.intui.standalone.theme.default
import org.jetbrains.jewel.intui.standalone.theme.lightThemeDefinition
import org.jetbrains.jewel.intui.window.decoratedWindow
import org.jetbrains.jewel.intui.window.styling.dark
import org.jetbrains.jewel.intui.window.styling.light
import org.jetbrains.jewel.intui.window.styling.lightWithLightHeader
import org.jetbrains.jewel.ui.ComponentStyling
import org.jetbrains.jewel.window.styling.DecoratedWindowStyle
import org.jetbrains.jewel.window.styling.TitleBarStyle

@Composable
fun AppCustomTheme(
    currentTheme: String? = null,
    content: @Composable () -> Unit
) {
    val currentTheme = Theme.fromString(currentTheme)
    val isDarkTheme = currentTheme.isDarkTheme()
    val themeDefinition =
        if (isDarkTheme) JewelTheme.darkThemeDefinition() else JewelTheme.lightThemeDefinition()
    CompositionLocalProvider(
        LocalCurrentTheme provides currentTheme
    ) {
        IntUiTheme(
            theme = themeDefinition,
            styling = ComponentStyling.default().decoratedWindow(
                titleBarStyle = getTitleBarStyleWithTheme(isDarkTheme),
                windowStyle = getWindowStyleWithTheme(isDarkTheme)
            ),
            content = content,
        )
    }
}


@Composable
private fun getTitleBarStyleWithTheme(isDark: Boolean) =
    if (isDark) TitleBarStyle.dark() else TitleBarStyle.lightWithLightHeader()

@Composable
private fun getWindowStyleWithTheme(isDark: Boolean) =
    if (isDark) DecoratedWindowStyle.dark() else DecoratedWindowStyle.light()


@Composable
private fun Theme.isDarkTheme() = when (this) {
    Theme.LIGHT -> false
    Theme.DARK -> true
    Theme.SYSTEM -> isSystemInDarkTheme()
}