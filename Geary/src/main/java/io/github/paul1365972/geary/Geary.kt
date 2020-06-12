package io.github.paul1365972.geary

import io.github.paul1365972.geary.event.PluginDisableListener
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

private lateinit var INSTANCE: Geary

class Geary : JavaPlugin() {
    companion object : Plugin by INSTANCE {
        fun forActiveItems(consumer: (ItemStack, Inventory, Player) -> Unit) {
            server.onlinePlayers.forEach { player ->
                player.inventory.let { inventory ->
                    inventory.forEach { item ->
                        consumer(item, inventory, player)
                    }
                }
            }
        }

        fun forActiveEntities(consumer: (Entity) -> Unit) {
            server.worlds.forEach { world ->
                world.entities.forEach { entity ->
                    consumer(entity)
                }
            }
        }
    }

    override fun onLoad() {
        INSTANCE = this
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(PluginDisableListener(), this)
    }

    override fun onDisable() {}
}
