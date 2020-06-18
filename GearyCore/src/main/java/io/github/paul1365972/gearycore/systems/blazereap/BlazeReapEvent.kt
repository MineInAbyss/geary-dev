package io.github.paul1365972.gearycore.systems.blazereap

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.attributes.EventAttribute
import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPhase
import io.github.paul1365972.geary.event.listener.EventPriority
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.events.EntitySourceEventAttribute
import io.github.paul1365972.gearycore.events.ItemSourceEventAttribute
import io.github.paul1365972.gearycore.events.UseEventAttribute
import io.github.paul1365972.gearycore.systems.durability.DurabilityUseEventAttribute
import io.github.paul1365972.gearycore.util.move
import org.bukkit.Location
import org.bukkit.entity.LivingEntity

data class BlazingExploderFireEventAttribute(
        var location: Location,
        var strength: Float,
        var destroyBlocks: Boolean
) : EventAttribute


class BlazingExploderUseListener : EventListener(
        GearyCorePlugin,
        EventPhase.INCUBATION,
        EventPriority.EARLIER
) {
    override fun handle(event: Event) = event.where<UseEventAttribute, ItemSourceEventAttribute, EntitySourceEventAttribute> { _, (item), (entity) ->
        item.blazingExploderComponent.ifPresent { blazingExploder ->
            event.remove<UseEventAttribute>()
            val location = if (entity is LivingEntity) entity.eyeLocation else entity.location
            event.add(BlazingExploderFireEventAttribute(location, blazingExploder.strength, blazingExploder.destroyBlocks))
            event.add(DurabilityUseEventAttribute())
        }
    }
}


class BlazingExploderFireListener : EventListener(
        GearyCorePlugin,
        EventPhase.EXECUTION
) {
    override fun handle(event: Event) = event.where<BlazingExploderFireEventAttribute> { (realLoc, strength, destroyBlocks) ->
        val entity = event.get<EntitySourceEventAttribute>()?.entity
        val loc = realLoc.clone()
        loc.add(loc.direction.multiply(2.0))
        var runs = 8
        GearyCorePlugin.server.scheduler.runTaskTimer(GearyCorePlugin, { task ->
            loc.world?.createExplosion(loc, strength * 2f,
                    false, destroyBlocks, entity)
            loc.move(2.0)
            if (--runs <= 0) task.cancel()
        }, 1, 4)
    }
}
