plugins {
    id(AppPlugins.KOTLIN)
    id(AppPlugins.COMPOSE)
}

dependencies {
    implementation(project(":shared:git"))
    implementation(project(":shared:utils:app"))
    implementation(project(":shared:preferences"))
    implementation(project(":shared:database"))
    implementation(project(":shared:locale"))
    implementation(project(":shared:resource"))
    implementation(project(":core:project"))
}