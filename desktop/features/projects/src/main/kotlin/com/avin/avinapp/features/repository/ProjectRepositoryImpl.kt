package com.avin.avinapp.features.repository

import com.avin.avinapp.core.builder.ProjectBuilder
import com.avin.avinapp.core.data.state.new_project.NewProjectStatus
import com.avin.avinapp.databases.AppDatabase
import com.avin.avinapp.databases.utils.asFlow
import com.avin.avinapp.features.data.mappers.toDataListProject
import com.avin.avinapp.features.data.models.Project
import com.avin.avinapp.time.getCurrentTimeMillis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProjectRepositoryImpl(private val database: AppDatabase, private val projectBuilder: ProjectBuilder) :
    ProjectRepository {
    override fun getProjects(): Flow<List<Project>> {
        return database
            .appDatabaseQueries
            .selectAllProjects()
            .asFlow()
            .map { it.executeAsList().toDataListProject() }
    }

    override fun insertProject(name: String, path: String) {
        return database.appDatabaseQueries.insertProject(
            name = name, path = path, createdAt = getCurrentTimeMillis()
        )
    }

    override fun searchProjects(query: String): Flow<List<Project>> {
        return database
            .appDatabaseQueries
            .selectProjectsByName(query)
            .asFlow()
            .map { it.executeAsList().toDataListProject() }
    }

    override fun createProject(
        name: String,
        path: String,
        withGit: Boolean
    ) = projectBuilder.newProject(name, path, withGit)
}