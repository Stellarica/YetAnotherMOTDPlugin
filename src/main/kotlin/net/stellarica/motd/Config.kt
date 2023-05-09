package net.stellarica.motd

data class Config(
	val global: String,
	val motds: List<String>
)
