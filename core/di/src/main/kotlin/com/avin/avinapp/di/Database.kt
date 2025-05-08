package com.avin.avinapp.di

import com.avin.avinapp.databases.providers.DatabaseProvider
import com.avin.avinapp.databases.providers.DatabaseProviderImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseProviderImpl() }.bind<DatabaseProvider>()
    single { get<DatabaseProvider>().provideDatabase() }
}