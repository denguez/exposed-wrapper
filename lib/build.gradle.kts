plugins {
    kotlin("jvm") version "1.5.0"
    `maven-publish`
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    // Postgres
	runtimeOnly("org.postgresql:postgresql:42.2.2")

    // Exposed
    api("org.jetbrains.exposed:exposed-core:0.31.1")
    api("org.jetbrains.exposed:exposed-dao:0.31.1")
    api("org.jetbrains.exposed:exposed-jdbc:0.31.1")
    api("org.jetbrains.exposed:exposed-java-time:0.31.1")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
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