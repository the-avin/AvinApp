package com.avin.avinapp.di

import com.avin.avinapp.data.providers.settings.configuration.SettingsConfigurationProvider
import com.avin.avinapp.data.providers.settings.configuration.SettingsConfigurationProviderImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val providersModule = module {
    singleOf(::SettingsConfigurationProviderImpl).bind<SettingsConfigurationProvider>()
}