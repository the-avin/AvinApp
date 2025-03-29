package com.avin.avinapp.di

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.avin.avinapp.components.RootComponent
import org.koin.dsl.module

val componentsModules = module {
    single {
        val lifecycle = LifecycleRegistry()
        val context = DefaultComponentContext(lifecycle = lifecycle)
        RootComponent(
            context = context
        )
    }
}