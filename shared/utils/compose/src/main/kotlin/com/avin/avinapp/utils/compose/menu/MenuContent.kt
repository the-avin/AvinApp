package com.avin.avinapp.utils.compose.menu

import com.avin.avinapp.locale.StringRes


sealed class MenuContent {
    data class Item(val stringRes: StringRes, val action: () -> Unit) : MenuContent()
    data class SubMenu(val stringRes: StringRes, val items: List<MenuContent>) : MenuContent()
    data object Separator : MenuContent()
}