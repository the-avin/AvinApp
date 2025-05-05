package com.avin.avinapp.utils.compose.nodes.navigation_bar

import com.avin.avinapp.locale.StringRes

data class NavigationBarItem<T : Any>(
    val name: StringRes,
    val icon: String,
    val page: T
)
