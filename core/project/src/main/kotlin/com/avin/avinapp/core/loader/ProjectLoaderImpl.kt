package com.avin.avinapp.core.loader

import com.avin.avinapp.core.data.models.project.ProjectManifest
import com.avin.avinapp.core.meta.ProjectMeta
import com.charleskorn.kaml.Yaml
import java.io.File

class ProjectLoaderImpl : ProjectLoader {
    override fun loadManifest(path: String): ProjectManifest {
        val file = File(path, ProjectMeta.PROJECT_MANIFEST_FILE_NAME)
        val fileContent = file.readText()
        val manifest = Yaml.default.decodeFromString(ProjectManifest.serializer(), fileContent)
        return manifest
    }

    override fun isValidProject(file: File): Boolean {
        return file.listFiles()?.any { it.name == ProjectMeta.PROJECT_MANIFEST_FILE_NAME && it.isFile } == true
    }

    override fun isValidProject(path: String): Boolean {
        return isValidProject(File(path))
    }
}