package com.avin.avinapp.data.models.project

import androidx.compose.runtime.Immutable
import com.avin.avinapp.core.loader.ProjectLoaderImpl

@Immutable
data class Project(
    val id: Long,
    val name: String,
    val path: String,
    val createdAt: Long
)


val Project.valid: Boolean get() = ProjectLoaderImpl.isValidProject(path)