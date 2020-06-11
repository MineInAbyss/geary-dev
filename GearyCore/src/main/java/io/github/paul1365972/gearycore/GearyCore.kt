package io.github.paul1365972.gearycore

import io.github.paul1365972.geary.event.GearyEventManager
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

private lateinit var INSTANCE: GearyCore

class GearyCore : JavaPlugin() {
    companion object : Plugin by INSTANCE

    override fun onLoad() {
        INSTANCE = this
    }

    override fun onEnable() {
        GearyEventManager.register(DurabilityListener())
    }
}
