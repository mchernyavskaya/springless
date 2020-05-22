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
    // logging
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.github.microutils:kotlin-logging:1.7.9")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    jar {
        manifest {
            attributes(mapOf("Main-Class" to "com.olx.example.WebApplicationKt"))
        }
    }
    val fatJar = task("fatJar", type = Jar::class) {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes["Implementation-Title"] = "Gradle Fat Jar File"
            attributes["Main-Class"] = "com.olx.example.WebApplicationKt"
        }
        from(configurations.runtimeClasspath.get().map {
            if (it.isDirectory) it else zipTree(it)
        })
        val jar: CopySpec by getting(Jar::class); with(jar)
    }

    build {
        dependsOn(fatJar)
    }
}
