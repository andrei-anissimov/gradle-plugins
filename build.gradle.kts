import java.net.URI

plugins {
    kotlin("jvm") version "2.1.10"
    `kotlin-dsl`
    `maven-publish`
}

apply(from = "gradle/files-script-plugin.gradle.kts")

gradlePlugin {
    plugins {
        create("filesPlugin") {
            id = "files-plugin"
            implementationClass = "io.anisand.plugin.files.FilesPlugin"
        }
    }
}

group = "io.anisand"
version = "0.1.1"

/*
docker run --name archiva -p 8080:8080 xetusoss/archiva
create account
*/
publishing {
    repositories {
        maven {
            url = URI("http://localhost:8080/repository/internal")
            name = "privateArchivaMaven"
            isAllowInsecureProtocol = true
            credentials {
                val privateArchivaUser: String? by project
                val privateArchivaPassword: String? by project
                username = privateArchivaUser
                password = privateArchivaPassword
            }
        }
    }
}

repositories {
    mavenCentral()
}
