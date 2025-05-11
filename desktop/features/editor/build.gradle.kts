plugins {
    id(AppPlugins.COMPOSE_UI)
    alias(libs.plugins.kotlin.serialization)
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    // Decompose
    implementation(libs.decompose)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":desktop:utils"))
    implementation(project(":core:theme"))
    implementation(project(":core:preview:base"))
    implementation(project(":core:preview:realtime"))
    implementation(project(":core:preview:snapshot"))
    implementation(project(":shared:data"))
    implementation(project(":shared:compose:dnd"))
    implementation(project(":shared:utils:app"))
    implementation(project(":shared:utils:compose"))
    implementation(project(":shared:locale"))
    implementation(project(":shared:resource"))
}