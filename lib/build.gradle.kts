plugins {
    kotlin("jvm") version "1.5.0"
    `maven-publish`
    `java-library`
}

repositories {
    mavenCentral()
}

val exposedV = "0.31.1"

dependencies {
    // Exposed
    api("org.jetbrains.exposed:exposed-core:${exposedV}")
    api("org.jetbrains.exposed:exposed-dao:${exposedV}")
    api("org.jetbrains.exposed:exposed-jdbc:${exposedV}")
    api("org.jetbrains.exposed:exposed-java-time:${exposedV}")

    // Kotlin
    implementation(platform(kotlin("bom")))
    implementation(kotlin("stdlib-jdk8"))
}

publishing {
    publications {
        val mavenJava by creating(MavenPublication::class) {
            groupId = "com.github.denguez"
            artifactId = "reify.db"
            version = "1.2"
            from(components["java"])
        }
    }
}