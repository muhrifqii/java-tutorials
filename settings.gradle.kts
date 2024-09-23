pluginManagement {
    includeBuild("gradle/build-logic")
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "Java-Tutorials"

include("functional-interfaces")
include("stream-api")
