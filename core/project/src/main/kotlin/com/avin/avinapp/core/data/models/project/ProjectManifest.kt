package com.avin.avinapp.core.data.models.project

import com.avin.avinapp.core.meta.ProjectMeta
import kotlinx.serialization.Serializable

@Serializable
data class ProjectManifest(
    val project: Project
) {
    @Serializable
    data class Project(
        val name: String,
        val version: String = ProjectMeta.PROJECT_DEFAULT_VERSION
    )
}