package com.avin.avinapp.data.models.project

import databases.Project as DbProject

fun DbProject.toDataProject(): Project {
    return Project(
        name = name,
        path = path,
        createdAt = createdAt,
        id = id
    )
}

fun List<DbProject>.toDataListProject(): List<Project> {
    return map { it.toDataProject() }
}