package com.avin.avinapp

import com.avin.avinapp.di.DependencyInjectionConfiguration
import com.avin.avinapp.di.modules
import com.avin.avinapp.utils.ImageLoaderConfiguration

fun main() {
    DependencyInjectionConfiguration.configure(*modules.toTypedArray())
    ImageLoaderConfiguration.configure()
    MainApp.init()
}