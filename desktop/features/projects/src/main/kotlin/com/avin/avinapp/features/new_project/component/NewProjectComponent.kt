package com.avin.avinapp.features.new_project.component

import com.arkivanov.decompose.ComponentContext
import com.avin.avinapp.components.BaseComponent
import com.avin.avinapp.core.data.state.new_project.NewProjectStatus
import com.avin.avinapp.core.data.state.new_project.isSuccess
import com.avin.avinapp.features.data.state.NewProjectState
import com.avin.avinapp.features.repository.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewProjectComponent(
    context: ComponentContext,
    private val repository: ProjectRepository
) : BaseComponent(context) {
    private val _state = MutableStateFlow(NewProjectState())
    val state = _state.asStateFlow()

    private val _status = MutableStateFlow<NewProjectStatus>(NewProjectStatus.Idle)
    val status = _status.asStateFlow()

    var pickedFromPicker = false

    fun updateState(newProjectState: NewProjectState) {
        if (!pickedFromPicker && newProjectState.name != _state.value.name) {
            _state.update { newProjectState.copy(path = NewProjectState.getPath(newProjectState.name)) }
        } else {
            _state.update { newProjectState }
        }
    }

    fun createProject() = scope.launch {
        repository.createProject(
            name = state.value.name,
            path = state.value.path,
            withGit = state.value.addToGit,
        ).collectLatest { newStatus ->
            if (newStatus.isSuccess()) {
                repository.insertProject(
                    _state.value.name,
                    _state.value.path,
                )
            }
            _status.update { newStatus }
        }
    }
}