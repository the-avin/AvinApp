package com.avin.avinapp.platform.file

import java.awt.Desktop
import java.io.File

object FileHandler {
    fun openFolder(path: String) {
        val file = File(path)
        if (!file.exists()) {
            throw IllegalArgumentException("Error: Folder not found at path: $path")
        }
        if (!Desktop.isDesktopSupported()) {
            throw UnsupportedOperationException("Error: Desktop API is not supported on this system")
        }
        Desktop.getDesktop().open(file)
    }
}