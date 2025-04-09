plugins {
    id(AppPlugins.KOTLIN)
    alias(libs.plugins.sqldelight)
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(libs.sqldelight.driver)
    implementation(libs.kotlinx.coroutines.core)


    implementation(project(":shared:utils:app"))
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName = "com.avin.avinapp.databases"
        }
    }
}

tasks.register("prepareDatabase") {
    dependsOn("generateSqlDelightInterface")

    doLast {
        println("SQLDelight interface generated successfully.")
    }
}