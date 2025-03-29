plugins {
    id(AppPlugins.KOTLIN)
}

repositories {
    google()
}

dependencies {
    implementation(libs.datastore.preferences)
    implementation(libs.datastore)

    implementation(project(":shared:utils:app"))
}