package com.avin.avinapp.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module

object DependencyInjectionConfiguration {
    fun configure(vararg modules: Module) {
        startKoin {
            modules(
                localModule,
                databaseModule,
                repositoryModule,
                otherModules,
                providersModule,
                *modules
            )
        }
    }
}