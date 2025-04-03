package com.avin.avinapp.features.data.state

import androidx.compose.runtime.Immutable
import com.avin.avinapp.core.meta.ProjectMeta

@Immutable
data class CloneRepositoryState(
    val url: String = "",
    val path: String = ProjectMeta.getDefaultProjectPath("")
)