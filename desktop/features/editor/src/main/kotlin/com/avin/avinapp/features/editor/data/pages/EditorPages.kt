package com.avin.avinapp.features.editor.data.pages

import com.avin.avinapp.locale.StringRes
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.utils.compose.nodes.navigation_bar.NavigationBarItem

sealed class EditorPages(
    val name: StringRes,
    val icon: String
) {
    data object Screens : EditorPages(
        name = Resource.string.open,
        icon = Resource.image.TEXT_CONTENT
    )

    data object Views : EditorPages(
        name = Resource.string.open,
        icon = Resource.image.GRAPH_VIEW
    )

    fun toNavigationBarItem() = NavigationBarItem(name, icon, this)

    companion object {
        val pages: List<EditorPages>
            get() = listOf(
                Screens,
                Views
            )

        val navigationPages: List<NavigationBarItem<EditorPages>>
            get() = pages.map { it.toNavigationBarItem() }
    }
}

val EditorPages.previewNeeded: Boolean
    get() = this is EditorPages.Views || this is EditorPages.Screens