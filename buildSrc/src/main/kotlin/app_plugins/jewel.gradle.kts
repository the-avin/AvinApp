package app_plugins

plugins {
    kotlin("jvm")
}

repositories {
    maven("https://packages.jetbrains.team/maven/p/kpm/public/")
    maven("https://www.jetbrains.com/intellij-repository/releases")
}

dependencies {
    implementation("org.jetbrains.jewel:jewel-int-ui-standalone-243:1.0.0-SNAPSHOT")
    implementation("org.jetbrains.jewel:jewel-int-ui-decorated-window-243:1.0.0-SNAPSHOT")
    implementation("com.jetbrains.intellij.platform:icons:243.25659.59")
}