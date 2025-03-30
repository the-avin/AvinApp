plugins {
    id(AppPlugins.KOTLIN)
    id("app.cash.sqldelight") version "2.0.2"
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("app.cash.sqldelight:sqlite-driver:2.0.2")
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

tasks.register<Exec>("generateDb") {
    commandLine("./gradlew", "generateSqlDelightInterface")
}