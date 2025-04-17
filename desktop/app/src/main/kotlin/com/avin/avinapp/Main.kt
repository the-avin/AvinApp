package com.avin.avinapp

import com.avin.avinapp.di.startDi
import com.avin.avinapp.utils.ImageLoaderConfiguration


fun main() {
    startDi()
    ImageLoaderConfiguration.configure()
    MainApp.init()
}