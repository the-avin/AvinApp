package com.avin.avinapp.features.repository

import com.avin.avinapp.core.data.state.new_project.NewProjectStatus
import com.avin.avinapp.features.data.models.Project
import com.avin.avinapp.git.utils.CustomProgressMonitor
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun getProjects(): Flow<List<Project>>
    fun insertProject(name: String, path: String)
    fun deleteProject(projectId: Long)
    fun searchProjects(query: String): Flow<List<Project>>
    fun createProject(name: String, path: String, withGit: Boolean): Flow<NewProjectStatus>
    fun canBuildProjectAtPath(path: String): Boolean
    fun cloneProject(path: String, url: String, progressMonitor: CustomProgressMonitor): Boolean
}