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

    // Material 3
    implementation(compose.material3)

    // Modules
    implementation(project(":core:theme"))
    implementation(project(":shared:data"))
    implementation(project(":shared:utils:compose"))
    implementation(project(":shared:utils:app"))
    implementation(project(":core:preview:base"))
}