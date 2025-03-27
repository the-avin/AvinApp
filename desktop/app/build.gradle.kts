import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id(AppPlugins.COMPOSE)
}

dependencies {
    implementation(project(":core:theme"))
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "AvinApp"
            packageVersion = "1.0.0"

            // For title bar theme
            jvmArgs(
                "-Dapple.awt.application.appearance=system"
            )
        }
    }
}
