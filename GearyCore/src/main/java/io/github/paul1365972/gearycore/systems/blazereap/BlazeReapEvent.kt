package io.github.paul1365972.gearycore.systems.blazereap

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.attributes.EventAttribute
import io.github.paul1365972.geary.event.attributes.EventAttributeFamily
import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPhase
import io.github.paul1365972.geary.event.listener.EventPriority
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.events.EntityEventAttribute
import io.github.paul1365972.gearycore.events.ItemEventAttribute
import io.github.paul1365972.gearycore.events.PlayerOwnedAttribute
import io.github.paul1365972.gearycore.events.UseEventAttribute
import io.github.paul1365972.gearycore.systems.durability.DurabilityUseEventAttribute
import org.bukkit.Location

data class BlazingExploderFireEventAttribute(
        var origin: Location,
        var strength: Float
) : EventAttribute


class BlazingExploderUseListener : EventListener(GearyCorePlugin,
        EventAttributeFamily(setOf(UseEventAttribute::class.java, ItemEventAttribute::class.java, PlayerOwnedAttribute::class.java)),
        EventPhase.INCUBATION, EventPriority.EARLIER) {
    override fun handle(event: Event) {
        val item = event.get<ItemEventAttribute>()!!
        item.itemStack.blazingExploderComponent?.let { blazingExploder ->
            val entity = event.get<PlayerOwnedAttribute>()!!
            event.remove<UseEventAttribute>()
            event.add(BlazingExploderFireEventAttribute(entity.player.eyeLocation, blazingExploder.strength))
            event.add(DurabilityUseEventAttribute(1))
        }
    }
}

class BlazingExploderFireListener : EventListener(GearyCorePlugin,
        EventAttributeFamily(setOf(BlazingExploderFireEventAttribute::class.java)),
        EventPhase.EXECUTION) {
    override fun handle(event: Event) {
        val fire = event.get<BlazingExploderFireEventAttribute>()!!

        val loc = fire.origin.clone()
        loc.add(loc.direction.multiply(4))
        var runs = 8
        GearyCorePlugin.server.scheduler.runTaskTimer(GearyCorePlugin, { task ->
            if (--runs <= 0 || !loc.block.isPassable) task.cancel()
            loc.world?.createExplosion(loc, fire.strength * 2f,
                    false, false, event.get<EntityEventAttribute>()?.entity)
            loc.add(loc.direction.multiply(2))
        }, 1, 4)

    }
}
