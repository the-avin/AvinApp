import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id(AppPlugins.COMPOSE_UI)
}

dependencies {
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)


    // Decompose
    implementation(libs.decompose)

    // FileKit
    implementation(libs.filekit.dialogs)

    // Kotlinx serialization
    implementation(libs.kotlinx.serialization.json)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.ktor)

    // Scripting
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:2.1.20")
    implementation("org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable:2.1.20")
    implementation("org.jetbrains.kotlin:kotlin-script-runtime:2.1.20")
    implementation("org.jetbrains.kotlin:kotlin-script-util:1.8.22")

    // Locale modules
    implementation(project(":core:theme"))
    implementation(project(":shared:database"))
    implementation(project(":shared:locale"))
    implementation(project(":shared:data"))
    implementation(project(":shared:resource"))
    implementation(project(":shared:preferences"))
    implementation(project(":shared:utils:app"))
    implementation(project(":shared:git"))
    implementation(project(":core:project"))

    // Features
    implementation(project(":desktop:features:projects"))
    implementation(project(":desktop:features:editor"))
    implementation(project(":desktop:features:settings"))
}

val nameSpace = "com.avin.avinapp"
val resourcesFolder = "src/main/resources"

compose.desktop {
    application {
        mainClass = "${nameSpace}.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = ProjectInfo.APP_NAME
            packageVersion = ProjectInfo.VERSION

            nativeDistributions {
                macOS {
                    iconFile.set(project.file("${resourcesFolder}/icon.icns"))
                }
                windows {
                    iconFile.set(project.file("${resourcesFolder}/icon.ico"))
                }
                linux {
                    iconFile.set(project.file("${resourcesFolder}/icon.png"))
                }
            }

            // For title bar theme
            jvmArgs(
                "-Dapple.awt.application.appearance=system"
            )
        }
    }
}