package com.avin.avinapp.features.projects.component

import androidx.compose.foundation.text.input.TextFieldState
import com.arkivanov.decompose.ComponentContext
import com.avin.avinapp.components.BaseComponent
import com.avin.avinapp.features.projects.repository.ProjectRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ProjectsComponent(context: ComponentContext, private val repository: ProjectRepository) : BaseComponent(context) {
    private val _searchValue = MutableStateFlow("")
    val searchValue = _searchValue.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val projects = _searchValue.debounce(100).flatMapLatest {
        if (it.isEmpty()) repository.getProjects() else repository.searchProjects(it)
    }.stateIn(scope, SharingStarted.Lazily, emptyList())


    fun search(value: String) {
        _searchValue.update { value }
    }
}