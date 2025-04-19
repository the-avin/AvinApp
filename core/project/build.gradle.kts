plugins {
    id(AppPlugins.COMPOSE)
    alias(libs.plugins.kotlin.serialization)
}


dependencies {
    // Yaml
    implementation(libs.kaml)

    // Modules
    implementation(project(":shared:git"))
    implementation(project(":shared:utils:app"))
}
