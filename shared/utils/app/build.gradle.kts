plugins {
    id(AppPlugins.KOTLIN)
}

dependencies {
    implementation(libs.decompose)

    implementation(libs.kotlinx.coroutines.core)

    implementation(project(":shared:locale"))
}