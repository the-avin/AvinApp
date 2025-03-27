package app_plugins

plugins {
    kotlin("jvm")
}

repositories {
    maven("https://packages.jetbrains.team/maven/p/kpm/public/")
}

dependencies {
    implementation("org.jetbrains.jewel:jewel-int-ui-standalone-241:0.27.0")
    implementation("org.jetbrains.jewel:jewel-int-ui-decorated-window-241:0.27.0")
}