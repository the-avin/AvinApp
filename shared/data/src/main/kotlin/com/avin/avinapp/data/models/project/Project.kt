package com.avin.avinapp.data.models.project

import androidx.compose.runtime.Immutable

@Immutable
data class Project(
    val id: Long,
    val name: String,
    val path: String,
    val createdAt: Long
)