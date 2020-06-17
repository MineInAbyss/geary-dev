package io.github.paul1365972.gearycore

import io.github.paul1365972.geary.GearyPlugin
import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.GearyEventManager
import io.github.paul1365972.gearycore.events.EntitySourceEventAttribute
import io.github.paul1365972.gearycore.events.ItemSourceEventAttribute
import io.github.paul1365972.gearycore.events.TickEventAttribute
import io.github.paul1365972.gearycore.systems.blazereap.BlazingExploderFireListener
import io.github.paul1365972.gearycore.systems.blazereap.BlazingExploderUseListener
import io.github.paul1365972.gearycore.systems.cooldown.CooldownApplier
import io.github.paul1365972.gearycore.systems.durability.DurabilityItemDegrader
import org.bukkit.plugin.java.JavaPlugin

class GearyCore : JavaPlugin() {
    override fun onEnable() {
        GearyCorePlugin.Holder.INSTANCE = this

        GearyEventManager.register(DurabilityItemDegrader())
        GearyEventManager.register(BlazingExploderUseListener())
        GearyEventManager.register(BlazingExploderFireListener())
        GearyEventManager.register(CooldownApplier())

        server.pluginManager.registerEvents(ActionListener(), this)
        server.scheduler.scheduleSyncRepeatingTask(this, {
            GearyPlugin.forPotentiallyActiveItems { itemStack, _, player ->
                Event(TickEventAttribute(), ItemSourceEventAttribute(itemStack), EntitySourceEventAttribute(player)).call()
            }
            GearyPlugin.forPotentiallyActiveEntities { entity ->
                Event(TickEventAttribute(), EntitySourceEventAttribute(entity)).call()
            }
        }, 1, 1)
    }
}
