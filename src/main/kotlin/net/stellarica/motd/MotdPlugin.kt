package net.stellarica.motd

import com.google.inject.Inject
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addFileSource
import com.velocitypowered.api.event.EventTask
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.text.minimessage.MiniMessage
import org.slf4j.Logger
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists

@Suppress("Unused")
@Plugin(
	id = "yamotdp",
	name = "Yet Another MOTD Plugin",
	version = "0.1.0",
	url = "https://github.com/Stellarica/YetAnotherMotdPlugin",
	description = "The simplest MOTD plugin",
	authors = ["trainb0y"]
)
class MotdPlugin @Inject constructor(
	val server: ProxyServer,
	val logger: Logger,
	@DataDirectory val dataDir: Path
) {
	lateinit var config: Config

	@Subscribe
	fun onProxyInit(event: ProxyInitializeEvent) {
		val configPath = dataDir.resolve("motd.conf")

		if (!configPath.exists()) {
			dataDir.createDirectories()
			Files.copy(this::class.java.getResource("/motd.conf")!!.openStream(), configPath)
		}

		config = ConfigLoaderBuilder.empty()
			.withClassLoader(this::class.java.classLoader)
			.addDefaults()
			.addFileSource(configPath.toFile())
			.build()
			.loadConfigOrThrow<Config>()
	}

	@Subscribe
	fun onGiveMOTD(event: ProxyPingEvent) = EventTask.async {
		event.ping = event.ping.asBuilder()
			.description(
				MiniMessage.miniMessage().deserialize(
					(if (config.global.isNotBlank()) config.global + "<reset>\n" else "")
						+ config.motds.randomOrNull()
			))
			.build()
	}
}