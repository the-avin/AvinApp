package com.avin.avinapp.features.editor.data.pages

import com.avin.avinapp.resource.Resource

sealed class EditorPages(val icon: String) {
    data object Screens : EditorPages(icon = Resource.image.FOLDER)
    data object Views : EditorPages(icon = Resource.image.FOLDER)

    companion object {
        val pages: List<EditorPages>
            get() = listOf(
                Screens,
                Views
            )
    }
}

val EditorPages.previewNeeded: Boolean
    get() = this is EditorPages.Views || this is EditorPages.Screens