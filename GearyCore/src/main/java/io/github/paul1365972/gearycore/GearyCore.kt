package io.github.paul1365972.gearycore

import io.github.paul1365972.geary.Geary
import io.github.paul1365972.geary.ecs.PlayerItemEntity
import io.github.paul1365972.geary.event.GearyEventManager
import io.github.paul1365972.gearycore.components.DurabilityListener
import io.github.paul1365972.gearycore.events.tick.EntityTickEvent
import io.github.paul1365972.gearycore.events.tick.ItemTickEvent
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
        server.scheduler.scheduleSyncRepeatingTask(this, {
            Geary.forActiveItems { itemStack, inventory, player ->
                ItemTickEvent(PlayerItemEntity(itemStack, inventory, player)).call()
            }
            Geary.forActiveEntities { entity ->
                EntityTickEvent(entity).call()
            }
        }, 1, 1)
    }
}
