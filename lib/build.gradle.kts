plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.31"
    `maven-publish`
    //`java-library`
}

group = "com.github.denguez"

repositories {
    mavenCentral()
}

dependencies {
    // Database
	runtimeOnly("org.postgresql:postgresql:42.2.2")
    implementation("org.jetbrains.exposed:exposed-core:0.31.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.31.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.31.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.31.1")

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}
