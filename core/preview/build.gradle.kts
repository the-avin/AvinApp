plugins {
    id(AppPlugins.COMPOSE)
    alias(libs.plugins.kotlin.serialization)
}


dependencies {
    implementation(libs.kotlinx.serialization.json)
}