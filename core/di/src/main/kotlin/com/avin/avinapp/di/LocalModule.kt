package com.avin.avinapp.di

import com.avin.avinapp.manager.language.LanguageManager
import com.avin.avinapp.manager.language.LanguageManagerImpl
import com.avin.avinapp.preferences.PreferencesStorage
import com.avin.avinapp.preferences.PreferencesStorageImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val localModule = module {
    single { PreferencesStorageImpl() }.bind<PreferencesStorage>()
    single { LanguageManagerImpl() }.bind<LanguageManager>()
}