plugins {
    kotlin("jvm") version "2.1.10"
    `kotlin-dsl`
    `maven-publish`
//    id("files-plugin") version "0.1.0"
}

apply(from = "gradle/files-script-plugin.gradle.kts")

gradlePlugin {
    plugins {
        create("files-plugin") {
            id = "files-plugin"
            implementationClass = "io.anisand.plugin.files.FilesPlugin"
        }
    }
}

group = "io.anisand"
version = "0.1.0"

repositories {
    mavenCentral()
}
