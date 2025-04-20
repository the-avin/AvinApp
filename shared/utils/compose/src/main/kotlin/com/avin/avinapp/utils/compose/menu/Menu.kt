package com.avin.avinapp.utils.compose.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.window.MenuBarScope
import androidx.compose.ui.window.MenuScope
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.utils.compose.foundation.menu.textItem
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.separator
import org.jetbrains.jewel.ui.component.MenuScope as JewelMenuScope

@Composable
fun MenuBarScope.renderMenu(menus: List<Menu>) {
    menus.forEach { menu ->
        Menu(
            text = dynamicStringRes(menu.name)
        ) {
            renderContent(menu.content)
        }
    }
}


@Composable
private fun MenuScope.renderContent(content: List<MenuContent>) {
    content.forEach { item ->
        when (item) {
            is MenuContent.SubMenu -> {
                Menu(text = dynamicStringRes(item.stringRes)) {
                    renderContent(item.items)
                }
            }

            is MenuContent.Item -> {
                Item(text = dynamicStringRes(item.stringRes), onClick = { item.action() })
            }

            is MenuContent.Separator -> Separator()
        }
    }
}

fun JewelMenuScope.renderContent(content: List<MenuContent>) {
    content.forEachIndexed { index, item ->
        when (item) {
            is MenuContent.SubMenu -> {
                submenu(submenu = { renderContent(item.items) }) {
                    Box(
                        modifier = Modifier.pointerHoverIcon(
                            PointerIcon.Hand
                        ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(dynamicStringRes(item.stringRes))
                    }
                }
            }

            is MenuContent.Item -> {
                textItem(
                    stringRes = item.stringRes,
                    isStartItem = index == 0,
                    isEndItem = index == content.size - 1,
                    onClick = { item.action() })
            }

            is MenuContent.Separator -> separator()
        }
    }
}