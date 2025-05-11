plugins {
    id(AppPlugins.COMPOSE)
}
dependencies {
    api(AppDependencies.PLATFORM)
    implementation(project(":shared:resource"))
}