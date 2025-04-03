package com.avin.avinapp.features.editor.component

import com.arkivanov.decompose.ComponentContext
import com.avin.avinapp.components.BaseComponent
import com.avin.avinapp.data.models.project.Project
import com.avin.avinapp.data.repository.project.ProjectRepository
import com.avin.avinapp.pages.AppPages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProjectEditorComponent(
    context: ComponentContext,
    private val info: AppPages.Editor,
    private val repository: ProjectRepository
) : BaseComponent(context) {
    private val _project = MutableStateFlow<Project?>(null)
    val project = _project.asStateFlow()


    init {
        loadProject()
    }

    private fun loadProject() = scope.launch {
        val newProject = runCatching { repository.getById(info.projectId) }.getOrNull()
        _project.update { newProject }
    }
}