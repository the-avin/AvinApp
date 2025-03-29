package com.avin.avinapp.utils

import java.io.File


object AppInfo {
    const val APP_FOLDER = ".avin"
    const val PREFERENCES_FILE = ".avin"

    private fun getHomeFolder() = System.getProperty("user.home")

    fun getAppFolderFile() {
        File(File(getHomeFolder(), APP_FOLDER), PREFERENCES_FILE)
    }
}