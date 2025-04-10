package com.avin.avinapp.pages

import kotlinx.serialization.Serializable

sealed class AppPages(val key: String) {
    @Serializable
    data object Projects : AppPages("projects")

    @Serializable
    data object NewProject : AppPages("new_project")

    @Serializable
    data object CloneRepository : AppPages("clone_repository")

    @Serializable
    data object Settings : AppPages("settings")

    @Serializable
    data class Editor(
        val projectId: Long
    ) : AppPages("editor") {
        companion object {
            const val KEY = "editor"
        }
    }
}