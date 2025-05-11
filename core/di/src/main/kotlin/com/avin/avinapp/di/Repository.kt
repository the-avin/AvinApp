package com.avin.avinapp.di

import com.avin.avinapp.data.repository.device.DeviceRepositoryImpl
import com.avin.avinapp.data.repository.device.DevicesRepository
import com.avin.avinapp.data.repository.editor_settings.EditorSettingsRepository
import com.avin.avinapp.data.repository.editor_settings.EditorSettingsRepositoryImpl
import com.avin.avinapp.data.repository.project.ProjectRepository
import com.avin.avinapp.data.repository.project.ProjectRepositoryImpl
import com.avin.avinapp.data.repository.widget.ComposableRepository
import com.avin.avinapp.data.repository.widget.ComposableRepositoryImpl
import com.avin.avinapp.data.serialization.JsonConfig
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module


val repositoryModule = module {
    factoryOf(::ProjectRepositoryImpl).bind<ProjectRepository>()
    factoryOf(::DeviceRepositoryImpl).bind<DevicesRepository>()
    factory {
        ComposableRepositoryImpl(json = get(named(JsonConfig.TYPED_JSON)))
    }.bind<ComposableRepository>()
    factoryOf(::EditorSettingsRepositoryImpl).bind<EditorSettingsRepository>()
}