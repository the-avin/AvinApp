package com.avin.avinapp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.intui.standalone.theme.darkThemeDefinition
import org.jetbrains.jewel.intui.standalone.theme.default
import org.jetbrains.jewel.intui.standalone.theme.lightThemeDefinition
import org.jetbrains.jewel.intui.window.decoratedWindow
import org.jetbrains.jewel.intui.window.styling.dark
import org.jetbrains.jewel.intui.window.styling.light
import org.jetbrains.jewel.ui.ComponentStyling
import org.jetbrains.jewel.window.styling.DecoratedWindowStyle
import org.jetbrains.jewel.window.styling.TitleBarStyle

@Composable
fun AppCustomTheme(content: @Composable () -> Unit) {
    val isDarkTheme = isSystemInDarkTheme()
    val themeDefinition = if (isDarkTheme) JewelTheme.darkThemeDefinition() else JewelTheme.lightThemeDefinition()
    IntUiTheme(
        theme = themeDefinition,
        styling = ComponentStyling.default().decoratedWindow(
            titleBarStyle = getTitleBarStyleWithTheme(isDarkTheme),
            windowStyle = getWindowStyleWithTheme(isDarkTheme)
        ),
        content = content,
    )
}


@Composable
private fun getTitleBarStyleWithTheme(isDark: Boolean) = if (isDark) TitleBarStyle.dark() else TitleBarStyle.light()

@Composable
private fun getWindowStyleWithTheme(isDark: Boolean) =
    if (isDark) DecoratedWindowStyle.dark() else DecoratedWindowStyle.light()