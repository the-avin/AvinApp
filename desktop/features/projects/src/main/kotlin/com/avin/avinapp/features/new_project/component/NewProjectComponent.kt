package com.avin.avinapp.features.new_project.component

import androidx.compose.runtime.*
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

    var projectAlreadyExistsError by mutableStateOf(false)
    var fieldsEmptyError by mutableStateOf(false)

    var pickedFromPicker = false

    fun updateState(newProjectState: NewProjectState) {
        if (_state.value.path != newProjectState.path) {
            pickedFromPicker = true
        }
        if (!pickedFromPicker && newProjectState.name != _state.value.name) {
            _state.update { newProjectState.copy(path = NewProjectState.getPath(newProjectState.name)) }
        } else {
            _state.update { newProjectState }
        }
        projectAlreadyExistsError = !canBuildProjectAtCurrentPath()
        fieldsEmptyError = checkFieldsEmptyError()
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

    private fun canBuildProjectAtCurrentPath() = repository.canBuildProjectAtPath(state.value.path)
    private fun checkFieldsEmptyError() = state.value.path.isEmpty() || state.value.name.isEmpty()
}