package com.avin.avinapp.utils.compose.nodes.navigation_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avin.avinapp.theme.icon.ColoredIcon
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.ToggleableIconButton
import org.jetbrains.jewel.window.defaultTitleBarStyle

@Composable
fun <T : Any> VerticalNavigationBar(
    currentPage: T,
    items: List<NavigationBarItem<T>>,
    onPageChanged: (T) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(JewelTheme.defaultTitleBarStyle.colors.background)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            ToggleableIconButton(
                value = item.page == currentPage,
                onValueChange = { onPageChanged.invoke(item.page) },
                modifier = Modifier.size(28.dp)
            ) {
                ColoredIcon(item.icon, modifier = Modifier.size(18.dp))
            }
        }
    }
}