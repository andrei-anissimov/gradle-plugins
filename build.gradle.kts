plugins {
    id("java")
}

apply(from = "gradle/filesPlugin.gradle.kts")

group = "io.anisand"
version = "1.0"

repositories {
    mavenCentral()
}
