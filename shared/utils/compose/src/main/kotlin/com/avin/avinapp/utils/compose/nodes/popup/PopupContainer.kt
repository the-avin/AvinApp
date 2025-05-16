package com.avin.avinapp.utils.compose.nodes.popup


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.avin.avinapp.utils.compose.foundation.positioning.SimpleAnchorVerticalMenuPositionProvider
import org.jetbrains.jewel.foundation.Stroke
import org.jetbrains.jewel.foundation.modifier.border
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.foundation.theme.OverrideDarkMode
import org.jetbrains.jewel.ui.component.styling.PopupContainerStyle
import org.jetbrains.jewel.ui.theme.popupContainerStyle

@Composable
fun PopupContainer(
    onDismissRequest: () -> Unit,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    modifier: Modifier = Modifier,
    style: PopupContainerStyle = JewelTheme.popupContainerStyle,
    popupProperties: PopupProperties = PopupProperties(focusable = true),
    content: @Composable () -> Unit,
) {
    val popupPositionProvider =
        SimpleAnchorVerticalMenuPositionProvider(
            contentOffset = style.metrics.offset,
            alignment = horizontalAlignment,
            density = LocalDensity.current,
        )
    Popup(
        popupPositionProvider = popupPositionProvider,
        onDismissRequest = onDismissRequest,
        properties = popupProperties,
    ) {
        OverrideDarkMode(style.isDark) {
            val colors = style.colors
            Box(
                modifier =
                    modifier
                        .shadow(
                            elevation = style.metrics.shadowSize,
                            ambientColor = colors.shadow,
                            spotColor = colors.shadow,
                        )
                        .border(
                            Stroke.Alignment.Inside,
                            style.metrics.borderWidth,
                            colors.border,
                        )
                        .background(colors.background)
            ) {
                content()
            }
        }
    }
}