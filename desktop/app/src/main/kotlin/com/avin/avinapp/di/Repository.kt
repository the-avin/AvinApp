package com.avin.avinapp.di

import com.avin.avinapp.features.repository.ProjectRepository
import com.avin.avinapp.features.repository.ProjectRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module


val repositoryModule = module {
    single { ProjectRepositoryImpl(get()) }.bind<ProjectRepository>()
}