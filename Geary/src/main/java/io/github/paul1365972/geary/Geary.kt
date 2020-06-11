package io.github.paul1365972.geary

import io.github.paul1365972.geary.base.events.EntityTickEvent
import io.github.paul1365972.geary.base.events.ItemTickEvent
import io.github.paul1365972.geary.event.PluginDisableListener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

private lateinit var INSTANCE: Geary

class Geary : JavaPlugin() {
    internal companion object : Plugin by INSTANCE

    override fun onLoad() {
        INSTANCE = this
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(PluginDisableListener(), this)
        server.scheduler.scheduleSyncRepeatingTask(this, {
            server.onlinePlayers.forEach { player ->
                player.inventory.let { inventory ->
                    inventory.forEach { item ->
                        ItemTickEvent(item, inventory, player).call()
                    }
                }
            }
            server.worlds.forEach { world ->
                world.entities.forEach { entity ->
                    EntityTickEvent(entity).call()
                }
            }
        }, 1, 1)
    }

    override fun onDisable() {}
}
