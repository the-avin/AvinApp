package com.avin.avinapp.utils.compose.menu

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.MenuBarScope
import androidx.compose.ui.window.MenuScope
import com.avin.avinapp.manager.compose.dynamicStringRes

@Composable
fun MenuBarScope.appendMenus(menus: List<Menu>) {
    menus.forEach { menu ->
        Menu(
            text = dynamicStringRes(menu.name)
        ) {
            appendContent(menu.content)
        }
    }
}


@Composable
fun MenuScope.appendContent(content: List<MenuContent>) {
    content.forEach { item ->
        when (item) {
            is MenuContent.SubMenu -> {
                Menu(text = dynamicStringRes(item.stringRes)) {
                    appendContent(item.items)
                }
            }

            is MenuContent.Item -> {
                Item(text = dynamicStringRes(item.stringRes), onClick = { item.action() })
            }

            is MenuContent.Separator -> Separator()
        }
    }
}