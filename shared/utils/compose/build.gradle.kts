plugins {
    id(AppPlugins.COMPOSE_UI)
}

repositories {
    mavenCentral()
}

dependencies {
    // File picker
    implementation(libs.filekit.dialogs)

    // Expression
    implementation(libs.exp4j)

    implementation(project(":shared:utils:app"))
    implementation(project(":shared:locale"))
    implementation(project(":shared:resource"))
    implementation(project(":core:theme"))
}