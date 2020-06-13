package io.github.paul1365972.gearycore

import io.github.paul1365972.geary.GearyService
import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.GearyEventManager
import io.github.paul1365972.gearycore.components.BlazingExploderFireListener
import io.github.paul1365972.gearycore.components.BlazingExploderUseListener
import io.github.paul1365972.gearycore.components.DurabilityItemDegrader
import io.github.paul1365972.gearycore.events.EntityEventComponent
import io.github.paul1365972.gearycore.events.ItemEventComponent
import io.github.paul1365972.gearycore.events.PlayerOwnedComponent
import io.github.paul1365972.gearycore.events.TickEventComponent
import org.bukkit.plugin.java.JavaPlugin

class GearyCore : JavaPlugin() {
    companion object {
        lateinit var INSTANCE: GearyCore
    }

    override fun onLoad() {
        INSTANCE = this
    }

    override fun onEnable() {
        GearyEventManager.register(DurabilityItemDegrader())
        GearyEventManager.register(BlazingExploderUseListener())
        GearyEventManager.register(BlazingExploderFireListener())
        server.pluginManager.registerEvents(ActionListener(), this)
        server.scheduler.scheduleSyncRepeatingTask(this, {
            GearyService.forPotentiallyActiveItems { itemStack, _, player ->
                Event(TickEventComponent(), ItemEventComponent(itemStack), PlayerOwnedComponent(player)).call()
            }
            GearyService.forPotentiallyActiveEntities { entity ->
                Event(TickEventComponent(), EntityEventComponent(entity)).call()
            }
        }, 1, 1)
    }
}
