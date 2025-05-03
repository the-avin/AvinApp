pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
        id("org.jetbrains.kotlin.plugin.compose").version(extra["kotlin.version"] as String)
    }
}

rootProject.name = "AvinApp"


include("desktop:app")
include("desktop:features:projects")
include("desktop:features:editor")
include("desktop:features:settings")
include("core:preview:base")
include("core:preview:providers")
include("core:preview:snapshot")
include("core:preview:realtime")
include("core:theme")
include("shared:resource")
include("shared:data")
include("shared:locale")
include("shared:utils:app")
include("shared:utils:compose")
include("shared:preferences")
include("shared:database")
include("shared:git")
include("core:project")
include("shared:platform")