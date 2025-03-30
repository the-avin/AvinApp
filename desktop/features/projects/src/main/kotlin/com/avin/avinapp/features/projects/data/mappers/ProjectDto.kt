package com.avin.avinapp.features.projects.data.mappers

import databases.Project as DbProject
import com.avin.avinapp.features.projects.data.models.Project as DataProject

fun DbProject.toDataProject(): DataProject {
    return DataProject(
        name = name,
        path = path,
        createdAt = createdAt,
        id = id
    )
}

fun List<DbProject>.toDataListProject(): List<DataProject> {
    return map { it.toDataProject() }
}