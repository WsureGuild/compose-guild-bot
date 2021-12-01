val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposedVersion:String by project
val brotliVersion = "1.6.0"
val operatingSystem: OperatingSystem = org.gradle.nativeplatform.platform.internal.DefaultNativePlatform.getCurrentOperatingSystem()
plugins {
    application
    kotlin("jvm") version "1.6.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "bot.tx.wsure.top"
version = "0.0.1"
application {
    mainClass.set("bot.tx.wsure.top.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-websockets:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-websockets:$ktor_version")
    implementation("io.ktor:ktor-client-okhttp:$ktor_version")
    implementation("io.ktor:ktor-client-serialization:$ktor_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("com.h2database:h2:1.4.200")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")

    implementation("com.aayushatharva.brotli4j:brotli4j:$brotliVersion")
    implementation(
        "com.aayushatharva.brotli4j:native-${
            if (operatingSystem.isWindows) "windows-x86_64"
            else if (operatingSystem.isMacOsX) "osx-x86_64"
            else if (operatingSystem.isLinux)
                if (org.gradle.nativeplatform.platform.internal.DefaultNativePlatform.getCurrentArchitecture().isArm) "linux-aarch64"
                else "native-linux-x86_64"
            else ""
        }:$brotliVersion"
    )

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}