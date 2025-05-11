plugins {
    id(AppPlugins.COMPOSE_UI)
    alias(libs.plugins.kotlin.serialization)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)

    // Kotlinx serialization
    implementation(libs.kotlinx.serialization.json)

    // Local modules
    implementation(project(":desktop:utils"))
    implementation(project(":shared:data"))
    implementation(project(":shared:git"))
    implementation(project(":shared:locale"))
    implementation(project(":shared:preferences"))
    implementation(project(":shared:database"))
    implementation(project(":core:project"))
}