package com.avin.avinapp.utils.compose.menu

import com.avin.avinapp.locale.StringRes

class MenuContentScope {
    private val _list = mutableListOf<MenuContent>()

    fun item(text: StringRes, action: () -> Unit): MenuContent.Item {
        return MenuContent.Item(text, action)
    }

    fun submenu(stringRes: StringRes, content: MenuContentScope.() -> Unit): MenuContent.SubMenu {
        val scope = MenuContentScope()
        scope.content()
        return MenuContent.SubMenu(stringRes, scope.asList())
    }

    fun separator(): MenuContent.Separator {
        return MenuContent.Separator
    }

    operator fun MenuContent.unaryPlus() {
        _list.add(this)
    }

    fun asList(): List<MenuContent> = _list.toList()
}


fun buildMenuContent(content: MenuContentScope.() -> Unit): List<MenuContent> {
    val scope = MenuContentScope()
    scope.content()
    return scope.asList()
}