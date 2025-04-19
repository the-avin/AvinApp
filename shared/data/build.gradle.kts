plugins {
    id(AppPlugins.KOTLIN)
    id(AppPlugins.COMPOSE)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // Serializer
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":shared:git"))
    implementation(project(":shared:utils:app"))
    implementation(project(":shared:preferences"))
    implementation(project(":shared:database"))
    implementation(project(":shared:locale"))
    implementation(project(":shared:resource"))
    implementation(project(":core:project"))
}