plugins {
    kotlin("jvm") version "1.5.0"
    `maven-publish`
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    // Exposed
    api("org.jetbrains.exposed:exposed-core:0.31.1")
    api("org.jetbrains.exposed:exposed-dao:0.31.1")
    api("org.jetbrains.exposed:exposed-jdbc:0.31.1")
    api("org.jetbrains.exposed:exposed-java-time:0.31.1")

    // Kotlin
    implementation(kotlin("jdk8"))
    implementation(platform(kotlin("bom")))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

publishing {
    publications {
        val mavenJava by creating(MavenPublication::class) {
            groupId = "com.github.denguez"
            artifactId = "reifydb"
            version = "1.2"
            from(components["java"])
        }
    }
}