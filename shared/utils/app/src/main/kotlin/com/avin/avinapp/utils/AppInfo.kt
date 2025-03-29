package com.avin.avinapp.utils

import java.io.File


object AppInfo {
    const val APP_FOLDER = ".avin"
    const val PREFERENCES_FILE = "preferences.preferences_pb"

    private fun getHomeFolder() = System.getProperty("user.home")

    fun getAppFolderFile(): File {
        return File(File(getHomeFolder(), APP_FOLDER), PREFERENCES_FILE)
    }
}