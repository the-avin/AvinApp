plugins {
    id(AppPlugins.COMPOSE_UI)
}

repositories {
    mavenCentral()
    google()
    maven("https://packages.jetbrains.team/maven/p/kpm/public/")
    maven("https://www.jetbrains.com/intellij-repository/releases")
}

dependencies {
    // Decompose
    implementation(libs.decompose)

    implementation(libs.jewel.foundation)

    implementation(project(":core:theme"))
    implementation(project(":shared:utils:app"))
    implementation(project(":shared:utils:compose"))
    implementation(project(":shared:locale"))
    implementation(project(":shared:resource"))
    implementation(project(":shared:data"))
}