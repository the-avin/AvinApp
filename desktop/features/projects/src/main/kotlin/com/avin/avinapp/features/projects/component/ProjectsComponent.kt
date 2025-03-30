package com.avin.avinapp.features.projects.component

import com.arkivanov.decompose.ComponentContext
import com.avin.avinapp.components.BaseComponent
import com.avin.avinapp.features.projects.repository.ProjectRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

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