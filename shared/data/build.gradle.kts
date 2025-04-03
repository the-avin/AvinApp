plugins {
    id(AppPlugins.KOTLIN)
}

dependencies {
    implementation(project(":shared:git"))
    implementation(project(":shared:utils:app"))
    implementation(project(":shared:database"))
    implementation(project(":core:project"))
}