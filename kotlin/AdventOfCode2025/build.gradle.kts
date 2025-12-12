plugins {
    kotlin("jvm") version "2.2.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.kittinunf.fuel", "fuel", "2.3.1")
    implementation("tools.aqua:z3-turnkey:4.14.1")
}

tasks.test {
    useJUnitPlatform()
}
