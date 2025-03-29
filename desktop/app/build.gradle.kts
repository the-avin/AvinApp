import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id(AppPlugins.COMPOSE_UI)
}

dependencies {
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)

    // Decompose
    implementation(libs.decompose)


    // Locale modules
    implementation(project(":core:theme"))
    implementation(project(":shared:locale"))
    implementation(project(":shared:resource"))
    implementation(project(":shared:preferences"))
    implementation(project(":shared:utils:app"))

    // Features
    implementation(project(":desktop:features:projects"))
}

val nameSpace = "com.avin.avinapp"

compose.desktop {
    application {
        mainClass = "${nameSpace}.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "AvinApp"
            packageVersion = "1.0.0"
            macOS { iconFile.set(project.file("icon.png")) }
            windows { iconFile.set(project.file("icon.png")) }
            linux { iconFile.set(project.file("icon.png")) }

            // For title bar theme
            jvmArgs(
                "-Dapple.awt.application.appearance=system"
            )
        }
    }
}


val appFolder: String by extra { ".avin" }