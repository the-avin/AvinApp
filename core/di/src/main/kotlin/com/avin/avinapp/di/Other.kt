package com.avin.avinapp.di

import com.avin.avinapp.core.builder.ProjectBuilder
import com.avin.avinapp.core.builder.ProjectBuilderImpl
import com.avin.avinapp.core.loader.ProjectLoader
import com.avin.avinapp.core.loader.ProjectLoaderImpl
import com.avin.avinapp.data.serialization.JsonConfig
import com.avin.avinapp.git.helper.GitHelper
import com.avin.avinapp.git.helper.GitHelperImpl
import com.avin.avinapp.git.manager.GitManager
import com.avin.avinapp.git.manager.GitManagerImpl
import com.avin.avinapp.shortcut.desktop.DesktopShortcutManager
import com.avin.avinapp.shortcut.platform.ShortcutManager
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val otherModules = module {
    singleOf(::GitHelperImpl).bind<GitHelper>()
    singleOf(::GitManagerImpl).bind<GitManager>()

    // Project
    singleOf(::ProjectBuilderImpl).bind<ProjectBuilder>()
    single { ProjectLoaderImpl }.bind<ProjectLoader>()

    // Json
    factory { Json { ignoreUnknownKeys = true; prettyPrint = true; encodeDefaults = true } }

    factory(named(JsonConfig.TYPED_JSON)) { JsonConfig.typeJson }

    // Utils
    factory { DesktopShortcutManager() }.bind<ShortcutManager>()
}