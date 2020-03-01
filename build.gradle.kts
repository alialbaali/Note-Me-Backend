val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.3.61"
}

group = "com.noteMe"
version = "0.2.5"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")

    // Netty
    implementation("io.ktor:ktor-server-netty:$ktor_version")

    // Log
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Ktor
    implementation("io.ktor:ktor-server-core:$ktor_version")

    // Test
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")

    // Jackson
    implementation("io.ktor:ktor-jackson:$ktor_version")

    // ExposedSQL
    implementation("org.jetbrains.exposed:exposed-core:0.21.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.21.1")

    // PostgresSQL
    implementation("org.postgresql:postgresql:42.2.2")

    // Java Time Module
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.8.6")

    // Auth
    implementation("io.ktor:ktor-auth:$ktor_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
