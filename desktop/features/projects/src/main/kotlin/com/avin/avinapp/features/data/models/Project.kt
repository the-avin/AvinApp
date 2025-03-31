package com.avin.avinapp.features.data.models

import androidx.compose.runtime.Immutable

@Immutable
data class Project(
    val id: Long,
    val name: String,
    val path: String,
    val createdAt: Long
)