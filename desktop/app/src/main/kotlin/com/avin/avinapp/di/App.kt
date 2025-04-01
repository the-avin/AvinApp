package com.avin.avinapp.di

import org.koin.core.context.startKoin

fun startDi() {
    startKoin {
        modules(
            localModule,
            componentsModules,
            databaseModule,
            repositoryModule,
            otherModules
        )
    }
}