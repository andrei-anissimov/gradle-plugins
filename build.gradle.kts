plugins {
    kotlin("jvm") version "2.1.10"
    `kotlin-dsl`
}

apply(from = "gradle/files-plugin.gradle.kts")

gradlePlugin {
    plugins {
        create("files-plugin") {
            id = "files-plugin-v2"
            implementationClass = "io.anisand.plugin.files.FilesPlugin"
        }
    }
}

group = "io.anisand"
version = "1.0"

repositories {
    mavenCentral()
}
