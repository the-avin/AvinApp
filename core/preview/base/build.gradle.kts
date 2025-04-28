plugins {
    id(AppPlugins.COMPOSE_UI)
    alias(libs.plugins.kotlin.serialization)
}

repositories {
    mavenCentral()
}

dependencies {
    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Coil
    implementation(libs.coil.compose)

    // Material 3
    implementation(compose.material3)

    // Kotlin Reflect
    implementation(libs.kotlin.reflect)

    // Modules
    implementation(project(":core:theme"))
    implementation(project(":shared:data"))
    implementation(project(":shared:utils:compose"))
    implementation(project(":shared:utils:app"))
}