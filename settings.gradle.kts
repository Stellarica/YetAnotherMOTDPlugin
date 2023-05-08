rootProject.name = "ExamplePlugin"
pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
	}

	plugins {
		val kotlin_version: String by settings
		val shadow_version: String by settings

		kotlin("jvm") version kotlin_version
		kotlin("kapt") version kotlin_version

		id("com.github.johnrengelman.shadow") version shadow_version
	}
}