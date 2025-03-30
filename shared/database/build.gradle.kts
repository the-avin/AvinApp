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
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName = "com.avin.avinapp.databases"
        }
    }
}