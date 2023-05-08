package net.stellarica.example.velocity

import com.google.inject.Inject
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addFileSource
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists

@Suppress("Unused")
@Plugin(
	id = "example",
	name = "ExamplePlugin",
	version = "0.1.0",
	url = "https://github.com/Stellarica/VelocityPluginTemplate",
	description = "Plugin? Yes?",
	authors = ["you"]
)
class ExamplePlugin @Inject constructor(
	val server: ProxyServer,
	val logger: Logger,
	@DataDirectory val dataDir: Path
) {
	lateinit var config: Config

	@Subscribe
	fun onProxyInit(event: ProxyInitializeEvent) {
		val configPath = dataDir.resolve("example.conf")

		if (!configPath.exists()) {
			dataDir.createDirectories()
			Files.copy(this::class.java.getResource("/example.conf")!!.openStream(), configPath)
		}

		config = ConfigLoaderBuilder.empty()
			.withClassLoader(this::class.java.classLoader)
			.addDefaults()
			.addFileSource(configPath.toFile())
			.build()
			.loadConfigOrThrow<Config>()
	}
}