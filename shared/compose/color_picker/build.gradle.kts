plugins {
    id(AppPlugins.COMPOSE_UI)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.compose.color.picker)

    implementation(project(":shared:utils:compose"))
    implementation(project(":shared:compose:eyedropper"))
}