plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("com.github.johnrengelman.shadow")
}

group = "net.stellarica"
version = "1.0-SNAPSHOT"

repositories {
    maven(uri("https://repo.papermc.io/repository/maven-public/"))
}

dependencies {
    implementation("com.sksamuel.hoplite:hoplite-core:${property("hoplite_version")}")
    implementation("com.sksamuel.hoplite:hoplite-hocon:${property("hoplite_version")}")
    compileOnly("com.velocitypowered:velocity-api:${property("velocity_version")}")
    kapt("com.velocitypowered:velocity-api:${property("velocity_version")}")
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

kotlin {
    jvmToolchain(17)
}
