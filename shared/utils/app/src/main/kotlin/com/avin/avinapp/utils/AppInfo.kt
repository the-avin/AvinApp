package com.avin.avinapp.utils

import java.io.File


object AppInfo {
    const val APP_FOLDER = ".avin"
    const val PROJECTS_FOLDER = "AvinProjects"
    const val PREFERENCES_FILE = "preferences.preferences_pb"
    const val DATABASE_FILE = "appdata.db"

    private fun getHomeFolder() = System.getProperty("user.home")

    fun getAppFolderFile(): File {
        return File(File(getHomeFolder(), APP_FOLDER), PREFERENCES_FILE)
    }

    fun getDefaultProjectsFolderFile(projectName: String): File {
        return File(File(getHomeFolder(), PROJECTS_FOLDER), projectName)
    }

    fun getAppDatabaseFolderFile(): File {
        return File(File(getHomeFolder(), APP_FOLDER), DATABASE_FILE)
    }
}