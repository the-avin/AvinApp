plugins {
    id(AppPlugins.COMPOSE_UI)
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    // Decompose
    implementation(libs.decompose)

    implementation(project(":desktop:utils"))
    implementation(project(":core:theme"))
    implementation(project(":shared:data"))
    implementation(project(":shared:git"))
    implementation(project(":shared:utils:app"))
    implementation(project(":shared:utils:compose"))
    implementation(project(":shared:locale"))
    implementation(project(":shared:resource"))
    implementation(project(":core:project"))
}