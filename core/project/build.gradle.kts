plugins {
    id(AppPlugins.COMPOSE)
    alias(libs.plugins.kotlin.serialization)
}


dependencies {
    implementation(project(":shared:git"))
    implementation(libs.kaml)
}
