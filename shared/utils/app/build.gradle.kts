plugins {
    id(AppPlugins.KOTLIN)
}

dependencies {
    implementation(libs.decompose)

    implementation(libs.kotlinx.coroutines.core)

    api(AppDependencies.PLATFORM)

    implementation(project(":shared:locale"))
}