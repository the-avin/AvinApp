plugins {
    id(AppPlugins.COMPOSE_UI)
}

repositories {
    mavenCentral()
}

dependencies {
    // File picker
    implementation(libs.filekit.dialogs)

    implementation(project(":shared:utils:app"))
    implementation(project(":core:theme"))
}