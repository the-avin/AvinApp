package com.avin.avinapp.di

import com.avin.avinapp.data.repository.project.ProjectRepository
import com.avin.avinapp.data.repository.project.ProjectRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val repositoryModule = module {
    singleOf(::ProjectRepositoryImpl).bind<ProjectRepository>()
}