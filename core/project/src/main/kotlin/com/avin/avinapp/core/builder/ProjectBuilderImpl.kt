package com.avin.avinapp.core.builder

import com.avin.avinapp.core.data.models.project.ProjectManifest
import com.avin.avinapp.core.data.state.new_project.NewProjectStatus
import com.avin.avinapp.core.meta.ProjectMeta
import com.avin.avinapp.git.manager.GitManager
import com.charleskorn.kaml.Yaml
import kotlinx.coroutines.flow.flow
import java.io.File

class ProjectBuilderImpl(private val gitManager: GitManager) : ProjectBuilder {
    override fun newProject(name: String, path: String, withGit: Boolean) = flow<NewProjectStatus> {
        runCatching {
            emit(NewProjectStatus.Creating)
            ensureFolderCreated(path)
            val manifest = generateManifest(path, name)
            if (withGit) {
                emit(NewProjectStatus.AddGit)
                gitManager.init(path, initialFiles = listOf(manifest.path))
            }
            emit(NewProjectStatus.Completed)
        }.onFailure {
            it.printStackTrace()
            emit(NewProjectStatus.Error)
        }
    }

    private fun ensureFolderCreated(path: String) {
        val file = File(path)
        if (file.exists().not()) file.mkdirs()
    }

    private fun generateManifest(path: String, name: String): File {
        val project = ProjectManifest.Project(name = name)
        val manifest = ProjectManifest(project = project)
        val yamlContent = Yaml.default.encodeToString(ProjectManifest.serializer(), manifest)
        val file = File(path, ProjectMeta.PROJECT_MANIFEST_FILE_NAME).apply {
            createNewFile()
        }
        file.writeText(yamlContent)
        return file
    }
}