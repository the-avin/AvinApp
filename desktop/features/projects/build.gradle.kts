plugins {
    id(AppPlugins.COMPOSE_UI)
}

dependencies {
    // Decompose
    implementation(libs.decompose)

    implementation(project(":core:theme"))
    implementation(project(":shared:utils:app"))
    implementation(project(":shared:utils:compose"))
    implementation(project(":shared:locale"))
    implementation(project(":shared:resource"))
}