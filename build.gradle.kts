val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposedVersion:String by project
val brotliVersion = "1.6.0"
val operatingSystem: OperatingSystem = org.gradle.nativeplatform.platform.internal.DefaultNativePlatform.getCurrentOperatingSystem()
plugins {
    application
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.31"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "bot.tx.wsure.top"
version = "0.0.1"
application {
    mainClass.set("bot.tx.wsure.top.ApplicationKt")
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    //todo push to mavenCenter : https://github.com/WsureDev/wsure-bililiver
    implementation("top.wsure.bililiver:wsure-bililiver:1.0-SNAPSHOT")

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

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-cbor:1.3.1")
    implementation("org.reflections:reflections:0.10.2")

    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("com.h2database:h2:1.4.200")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")

    implementation("org.mapdb:mapdb:3.0.8")
    implementation("org.ehcache:ehcache:3.9.7")
    implementation("com.esotericsoftware:kryo:5.2.0")

    implementation("it.justwrote:kjob-core:0.2.0")
    implementation("it.justwrote:kjob-kron:0.2.0")

    implementation("it.skrape:skrapeit:1.1.5")

    implementation("it.justwrote:kjob-inmem:0.2.0") // for in-memory 'persistence' (e.g. tests)

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}