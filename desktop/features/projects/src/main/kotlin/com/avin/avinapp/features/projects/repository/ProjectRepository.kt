package com.avin.avinapp.features.projects.repository

import com.avin.avinapp.features.projects.data.models.Project
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun getProjects(): Flow<List<Project>>
    fun insertProject(name: String, path: String)
    fun searchProjects(query: String): Flow<List<Project>>
}