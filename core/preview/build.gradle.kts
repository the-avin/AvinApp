plugins {
    id(AppPlugins.COMPOSE_UI)
    alias(libs.plugins.kotlin.serialization)
}


dependencies {
    implementation(libs.kotlinx.serialization.json)

    // Coil
    implementation(libs.coil.compose)


    implementation(project(":core:theme"))
    implementation(project(":shared:utils:compose"))
}