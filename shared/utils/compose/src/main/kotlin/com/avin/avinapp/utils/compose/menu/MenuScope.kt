package com.avin.avinapp.utils.compose.menu

import com.avin.avinapp.locale.StringRes

data class Menu(
    val name: StringRes,
    val content: List<MenuContent>
)

class MenuScope {
    private val _menus = mutableListOf<Menu>()

    fun menu(stringRes: StringRes, content: MenuContentScope.() -> Unit): Menu {
        val scope = MenuContentScope()
        scope.content()
        return Menu(name = stringRes, scope.asList())
    }

    operator fun Menu.unaryPlus() {
        _menus += this
    }

    fun asList(): List<Menu> = _menus
}

fun buildMenu(menu: MenuScope.() -> Unit): List<Menu> {
    val scope = MenuScope()
    scope.menu()
    return scope.asList()
}