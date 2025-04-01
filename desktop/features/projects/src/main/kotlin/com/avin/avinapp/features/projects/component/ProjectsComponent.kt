package com.avin.avinapp.features.projects.component

import com.arkivanov.decompose.ComponentContext
import com.avin.avinapp.components.BaseComponent
import com.avin.avinapp.features.repository.ProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class ProjectsComponent(context: ComponentContext, private val repository: ProjectRepository) : BaseComponent(context) {
    private val _searchValue = MutableStateFlow("")
    val searchValue = _searchValue.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val projects = _searchValue.flatMapLatest {
        _loading.update { true }
        val newFlow = if (it.isEmpty()) repository.getProjects() else repository.searchProjects(it)
        _loading.update { false }
        newFlow
    }.flowOn(Dispatchers.IO).stateIn(scope, SharingStarted.Lazily, emptyList())


    fun search(value: String) {
        _searchValue.update { value }
    }
}