package app_plugins

plugins {
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("app_plugins.kotlin")
}

dependencies {
    api(compose.desktop.currentOs)
}