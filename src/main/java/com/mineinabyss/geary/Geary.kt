package com.mineinabyss.geary

import com.mineinabyss.geary.base.events.EntityTickEvent
import com.mineinabyss.geary.base.events.ItemTickEvent
import com.mineinabyss.geary.event.PluginDisableListener
import org.bukkit.plugin.java.JavaPlugin

class Geary : JavaPlugin() {
    internal companion object {
        lateinit var PLUGIN: Geary
    }

    override fun onLoad() {
        PLUGIN = this
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
