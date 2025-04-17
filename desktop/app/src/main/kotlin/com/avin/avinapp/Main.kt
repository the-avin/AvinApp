package com.avin.avinapp

import com.avin.avinapp.di.startDi
import com.avin.avinapp.utils.ImageLoader


fun main() {
    startDi()
    ImageLoader.configure()
    MainApp.init()
}