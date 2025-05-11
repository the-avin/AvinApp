plugins {
    id(AppPlugins.KOTLIN)
    id(AppPlugins.COMPOSE)
}

dependencies {
    implementation(project(":shared:utils:app"))
}