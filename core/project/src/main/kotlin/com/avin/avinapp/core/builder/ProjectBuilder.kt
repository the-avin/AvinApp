package com.avin.avinapp.core.builder

import com.avin.avinapp.core.data.state.new_project.NewProjectStatus
import kotlinx.coroutines.flow.Flow

interface ProjectBuilder {
    fun newProject(
        name: String,
        path: String,
        withGit: Boolean
    ): Flow<NewProjectStatus>
}