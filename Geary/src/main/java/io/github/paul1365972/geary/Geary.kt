package io.github.paul1365972.geary

import io.github.paul1365972.geary.event.PluginDisableListener
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin

class Geary : JavaPlugin(), GearyService {

    override fun onEnable() {
        server.servicesManager.register(GearyService::class.java, this, this, ServicePriority.Lowest)
        server.pluginManager.registerEvents(PluginDisableListener(), this)
    }

    override fun onDisable() {}

    override fun forPotentiallyActiveItems(consumer: (ItemStack, Inventory, Player) -> Unit) {
        server.onlinePlayers.forEach { player ->
            player.inventory.let { inventory ->
                inventory.forEach { item ->
                    if (item?.itemMeta?.persistentDataContainer?.isEmpty == false)
                        consumer(item, inventory, player)
                }
            }
        }
    }

    override fun forPotentiallyActiveEntities(consumer: (Entity) -> Unit) {
        server.worlds.forEach { world ->
            world.entities.forEach { entity ->
                if (entity?.persistentDataContainer?.isEmpty == false)
                    consumer(entity)
            }
        }
    }
}
