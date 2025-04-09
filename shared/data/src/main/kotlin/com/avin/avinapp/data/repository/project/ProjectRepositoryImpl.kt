package com.avin.avinapp.data.repository.project

import com.avin.avinapp.core.builder.ProjectBuilder
import com.avin.avinapp.data.models.project.Project
import com.avin.avinapp.data.models.project.toDataListProject
import com.avin.avinapp.data.models.project.toDataProject
import com.avin.avinapp.databases.AppDatabase
import com.avin.avinapp.databases.utils.asFlow
import com.avin.avinapp.databases.utils.runGettingLastId
import com.avin.avinapp.git.manager.GitManager
import com.avin.avinapp.git.utils.CustomProgressMonitor
import com.avin.avinapp.time.getCurrentTimeMillis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProjectRepositoryImpl(
    private val database: AppDatabase,
    private val projectBuilder: ProjectBuilder,
    private val gitManager: GitManager
) : ProjectRepository {
    override fun getProjects(): Flow<List<Project>> {
        return database
            .appDatabaseQueries
            .selectAllProjects()
            .asFlow()
            .map { it.executeAsList().toDataListProject() }
    }

    override fun insertProject(name: String, path: String): Long {
        return database.runGettingLastId {
            database.appDatabaseQueries.insertProject(
                name = name, path = path, createdAt = getCurrentTimeMillis()
            )
        }
    }

    override fun deleteProject(projectId: Long) {
        database.appDatabaseQueries.deleteProjectById(projectId)
    }

    override fun searchProjects(query: String): Flow<List<Project>> {
        return database
            .appDatabaseQueries
            .selectProjectsByName(query)
            .asFlow()
            .map { it.executeAsList().toDataListProject() }
    }

    override fun getById(id: Long): Project {
        return database.appDatabaseQueries.selectProjectById(id).executeAsOne().toDataProject()
    }

    override fun createProject(
        name: String,
        path: String,
        withGit: Boolean
    ) = projectBuilder.newProject(name, path, withGit)

    override fun canBuildProjectAtPath(path: String) = projectBuilder.canBuildProject(path)
    override fun cloneProject(path: String, url: String, progressMonitor: CustomProgressMonitor) =
        gitManager.clone(path, url, progressMonitor)
}