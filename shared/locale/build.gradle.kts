plugins {
    id(AppPlugins.COMPOSE)
}
dependencies {
    implementation(AppDependencies.PLATFORM)
    implementation(project(":shared:resource"))
}