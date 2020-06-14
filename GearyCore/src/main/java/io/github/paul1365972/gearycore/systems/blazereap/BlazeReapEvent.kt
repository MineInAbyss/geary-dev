package io.github.paul1365972.gearycore.systems.blazereap

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.attributes.EventAttribute
import io.github.paul1365972.geary.event.attributes.EventAttributeFamily
import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPhase
import io.github.paul1365972.geary.event.listener.EventPriority
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.events.EntitySourceEventAttribute
import io.github.paul1365972.gearycore.events.ItemEventAttribute
import io.github.paul1365972.gearycore.events.UseEventAttribute
import io.github.paul1365972.gearycore.systems.cooldown.CooldownEventAttribute
import io.github.paul1365972.gearycore.systems.cooldown.cooldownComponent
import io.github.paul1365972.gearycore.systems.durability.DurabilityUseEventAttribute
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import kotlin.math.roundToLong

data class BlazingExploderFireEventAttribute(
        var origin: Location,
        var strength: Float
) : EventAttribute


class BlazingExploderUseListener : EventListener(GearyCorePlugin,
        EventAttributeFamily(setOf(UseEventAttribute::class.java, ItemEventAttribute::class.java, EntitySourceEventAttribute::class.java), setOf()),
        EventPhase.INCUBATION, EventPriority.EARLIER) {
    override fun handle(event: Event) {
        val item = event.get<ItemEventAttribute>()!!.itemStack
        item.blazingExploderComponent?.let { blazingExploder ->
            event.remove<UseEventAttribute>()
            val entity = event.get<EntitySourceEventAttribute>()!!.entity
            item.cooldownComponent?.let { (nextUse, max) ->
                //TODO
                if (nextUse >= (System.currentTimeMillis() / 1000.0 * 20).roundToLong()) return else event.add(CooldownEventAttribute(max))
            }
            val location = if (entity is LivingEntity) entity.eyeLocation else entity.location
            event.add(BlazingExploderFireEventAttribute(location, blazingExploder.strength))
            event.modify({ DurabilityUseEventAttribute(1) }) { it.durabilityUsage += 1 }
        }
    }
}


class BlazingExploderFireListener : EventListener(GearyCorePlugin,
        EventAttributeFamily(setOf(BlazingExploderFireEventAttribute::class.java)),
        EventPhase.EXECUTION) {
    override fun handle(event: Event) {
        val fire = event.get<BlazingExploderFireEventAttribute>()!!

        val loc = fire.origin.clone()
        var runs = 8
        GearyCorePlugin.server.scheduler.runTaskTimer(GearyCorePlugin, { task ->
            loc.add(loc.direction.multiply(2))
            if (--runs <= 0 || !loc.block.isPassable) task.cancel()
            loc.world?.createExplosion(loc, fire.strength * 2f,
                    false, false, event.get<EntitySourceEventAttribute>()?.entity)

        }, 1, 4)
    }
}
