package com.avin.avinapp.features.clone.component

import com.arkivanov.decompose.ComponentContext
import com.avin.avinapp.components.BaseComponent
import com.avin.avinapp.core.meta.ProjectMeta
import com.avin.avinapp.features.data.state.CloneRepositoryState
import com.avin.avinapp.features.data.status.CloneRepositoryStatus
import com.avin.avinapp.features.repository.ProjectRepository
import com.avin.avinapp.git.manager.GitManager
import com.avin.avinapp.git.utils.CustomProgressMonitor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class CloneRepositoryComponent(
    context: ComponentContext,
    private val repository: ProjectRepository
) : BaseComponent(context) {
    var wasSetPathManual = false

    private val _state = MutableStateFlow(CloneRepositoryState())
    val state = _state.asStateFlow()

    private val _status = MutableStateFlow<CloneRepositoryStatus>(CloneRepositoryStatus.Idle)
    val status = _status.asStateFlow()

    private fun onEndCloning() {
        scope.launch {
            repository.insertProject(GitManager.getRepositoryNameByURI(_state.value.url), _state.value.path)
            _status.update { CloneRepositoryStatus.Completed }
        }
    }

    private val progressMonitor by lazy {
        CustomProgressMonitor(
            onEnd = ::onEndCloning,
            beginTask = { title, totalWorks ->
                _status.update {
                    if (it is CloneRepositoryStatus.Loading) {
                        it.copy(totalWorks = totalWorks, title = title)
                    } else CloneRepositoryStatus.Loading(totalWorks = totalWorks, title = title)
                }
            },
            onStart = { totalWorks -> _status.update { CloneRepositoryStatus.Loading(totalWorks = totalWorks) } },
            onCompletedTasksUpdate = { completed ->
                _status.update {
                    if (it is CloneRepositoryStatus.Loading) {
                        it.copy(currentWork = completed)
                    } else CloneRepositoryStatus.Loading(currentWork = completed)
                }
            },
        )
    }

    fun updateState(newState: CloneRepositoryState) {
        if (_state.value.path != newState.path) {
            wasSetPathManual = true
        }
        if (!wasSetPathManual) {
            _state.update {
                newState.copy(
                    path = ProjectMeta.getDefaultProjectPath(
                        GitManager.getRepositoryNameByURI(newState.url)
                    )
                )
            }
        } else {
            _state.update { newState }
        }
    }


    fun cloneProject() = scope.launch {
        val file = File(_state.value.path)
        if (file.exists().not()) file.mkdirs()
        _status.update { CloneRepositoryStatus.Loading(title = "Retrieve information") }
        repository.cloneProject(
            path = _state.value.path,
            url = _state.value.url,
            progressMonitor = progressMonitor
        )
    }
}