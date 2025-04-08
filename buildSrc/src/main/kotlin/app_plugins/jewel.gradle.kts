package app_plugins

plugins {
    kotlin("jvm")
}

repositories {
    maven("https://packages.jetbrains.team/maven/p/kpm/public/")
    maven("https://www.jetbrains.com/intellij-repository/releases")
}

dependencies {
    implementation("org.jetbrains.jewel:jewel-int-ui-standalone-243:0.26.0")
    implementation("org.jetbrains.jewel:jewel-int-ui-decorated-window-243:0.26.0")
    implementation("com.jetbrains.intellij.platform:icons:243.25659.59")
}