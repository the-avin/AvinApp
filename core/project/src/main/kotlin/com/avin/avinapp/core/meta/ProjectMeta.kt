package com.avin.avinapp.core.meta

import com.avin.avinapp.utils.AppInfo

object ProjectMeta {
    const val PROJECT_DEFAULT_VERSION = "1.0.0"
    const val PROJECT_MANIFEST_FILE_NAME = "manifest.yaml"


    fun getDefaultProjectPath(name: String): String {
        val baseName = name.replace(" ", "")
        return AppInfo.getDefaultProjectsFolderFile(baseName).path
    }
}