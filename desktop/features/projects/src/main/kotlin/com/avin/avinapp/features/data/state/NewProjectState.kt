package com.avin.avinapp.features.data.state

import androidx.compose.runtime.Immutable
import com.avin.avinapp.utils.AppInfo

@Immutable
data class NewProjectState(
    val name: String = DEFAULT_PROJECT_NAME,
    val path: String = createDefaultProjectsFolderPath(),
    val addToGit: Boolean = false
) {
    companion object {
        private const val DEFAULT_PROJECT_NAME = "My Application"

        private fun createDefaultProjectsFolderPath(): String {
            val baseName = DEFAULT_PROJECT_NAME.replace(" ", "")
            val baseFolder = AppInfo.getDefaultProjectsFolderFile(baseName)

            if (!baseFolder.exists()) return baseFolder.path

            var index = 1
            while (true) {
                val newFolder = AppInfo.getDefaultProjectsFolderFile("$baseName$index")
                if (!newFolder.exists()) return newFolder.path
                index++
            }
        }

        fun getPath(name: String): String {
            val baseName = name.replace(" ", "")
            return AppInfo.getDefaultProjectsFolderFile(baseName).path
        }
    }
}
