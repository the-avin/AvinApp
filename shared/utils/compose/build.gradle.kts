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
    implementation(project(":shared:locale"))
    implementation(project(":shared:resource"))
    implementation(project(":core:theme"))
}