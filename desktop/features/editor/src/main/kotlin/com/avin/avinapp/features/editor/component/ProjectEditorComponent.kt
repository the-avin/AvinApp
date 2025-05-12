package com.avin.avinapp.features.editor.component

import com.arkivanov.decompose.ComponentContext
import com.avin.avinapp.components.BaseComponent
import com.avin.avinapp.data.models.device.PreviewDevice
import com.avin.avinapp.data.models.editor_settings.ProjectEditorSettings
import com.avin.avinapp.data.models.project.Project
import com.avin.avinapp.data.models.descriptor.composable.ComposableDescriptor
import com.avin.avinapp.data.repository.device.DevicesRepository
import com.avin.avinapp.data.repository.editor_settings.EditorSettingsRepository
import com.avin.avinapp.data.repository.project.ProjectRepository
import com.avin.avinapp.data.repository.widget.ComposableRepository
import com.avin.avinapp.features.editor.data.pages.EditorPages
import com.avin.avinapp.pages.AppPages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProjectEditorComponent(
    context: ComponentContext,
    private val info: AppPages.Editor,
    private val repository: ProjectRepository,
    private val devicesRepository: DevicesRepository,
    private val composableRepository: ComposableRepository,
    editorSettingsRepository: EditorSettingsRepository
) : BaseComponent(context) {
    private val _project = MutableStateFlow<Project?>(null)
    val project = _project.asStateFlow()

    private val _recentProjects = MutableStateFlow<List<Project>>(emptyList())
    val recentProjects = _recentProjects.asStateFlow()

    private val _currentPage = MutableStateFlow<EditorPages>(EditorPages.Screens)
    val currentPage = _currentPage.asStateFlow()

    private val _devices = MutableStateFlow<List<PreviewDevice>>(emptyList())
    val devices = _devices.asStateFlow()

    private val _descriptors = MutableStateFlow<List<ComposableDescriptor>>(emptyList())
    val descriptors = _descriptors.asStateFlow()

    val editorSettings = editorSettingsRepository.getEditorSettings()
        .stateIn(scope, SharingStarted.WhileSubscribed(), ProjectEditorSettings())

    init {
        loadData()
        loadRecentProjects()
        loadDescriptors()
    }

    private fun loadDescriptors() = scope.launch(Dispatchers.IO) {
        val newDescriptors = composableRepository.getAllComposableDescriptors()
        _descriptors.update { newDescriptors }
    }

    private fun loadData() = scope.launch(Dispatchers.IO) {
        loadProject()
        loadDevices()
    }


    private fun loadDevices() {
        val newDevices = devicesRepository.getAllDevices()
        _devices.update { newDevices }
    }

    private fun loadRecentProjects() = scope.launch(Dispatchers.IO) {
        repository.getProjects()
            .map { it.filter { project -> project.id != info.projectId } }
            .collectLatest { newProjects ->
                _recentProjects.update { newProjects }
            }
    }

    private fun loadProject() {
        val newProject = runCatching { repository.getById(info.projectId) }.getOrNull()
        _project.update { newProject }
    }

    fun changePage(page: EditorPages) {
        _currentPage.update { page }
    }
}