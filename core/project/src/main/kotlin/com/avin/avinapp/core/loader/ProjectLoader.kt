package com.avin.avinapp.core.loader

import com.avin.avinapp.core.data.models.project.ProjectManifest
import java.io.File

interface ProjectLoader {
    fun loadManifest(path: String): ProjectManifest
    fun isValidProject(file: File): Boolean
    fun isValidProject(path: String): Boolean
}