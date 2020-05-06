class Versions {
    companion object {
        const val KTOR_VERSION: String = "1.3.2"
    }
}

plugins {
    kotlin("jvm") version KotlinVersion.CURRENT.toString()
    kotlin("kapt") version KotlinVersion.CURRENT.toString()
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor-server-core:${Versions.KTOR_VERSION}")
    implementation("io.ktor:ktor-server-netty:${Versions.KTOR_VERSION}")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}
