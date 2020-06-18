package io.github.paul1365972.gearycore.systems.blazereap

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.attributes.EventAttribute
import io.github.paul1365972.geary.event.attributes.EventAttributeFamily
import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPhase
import io.github.paul1365972.geary.event.listener.EventPriority
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.events.EntitySourceEventAttribute
import io.github.paul1365972.gearycore.events.ItemSourceEventAttribute
import io.github.paul1365972.gearycore.events.UseEventAttribute
import io.github.paul1365972.gearycore.systems.cooldown.CooldownEventAttribute
import io.github.paul1365972.gearycore.systems.cooldown.cooldownComponent
import io.github.paul1365972.gearycore.systems.durability.DurabilityUseEventAttribute
import org.bukkit.FluidCollisionMode
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import org.bukkit.util.BlockIterator
import kotlin.math.ceil
import kotlin.math.roundToLong

data class BlazingExploderFireEventAttribute(
        var location: Location,
        var strength: Float,
        var destroyBlocks: Boolean
) : EventAttribute


class BlazingExploderUseListener : EventListener(
        GearyCorePlugin,
        EventAttributeFamily(setOf(UseEventAttribute::class.java, ItemSourceEventAttribute::class.java, EntitySourceEventAttribute::class.java), setOf()),
        EventPhase.INCUBATION,
        EventPriority.EARLIER
) {
    override fun handle(event: Event) {
        val item = event.get<ItemSourceEventAttribute>()!!.itemStack
        item.blazingExploderComponent.get()?.let { blazingExploder ->
            event.remove<UseEventAttribute>()
            val entity = event.get<EntitySourceEventAttribute>()!!.entity
            item.cooldownComponent.modify {
                if (nextUse >= (System.currentTimeMillis() / 1000.0 * 20).roundToLong()) return else event.add(CooldownEventAttribute(cooldown))
            }
            val location = if (entity is LivingEntity) entity.eyeLocation else entity.location
            event.add(BlazingExploderFireEventAttribute(location, blazingExploder.strength, blazingExploder.destroyBlocks))
            event.add(DurabilityUseEventAttribute())
        }
    }
}


class BlazingExploderFireListener : EventListener(
        GearyCorePlugin,
        EventAttributeFamily(setOf(BlazingExploderFireEventAttribute::class.java)),
        EventPhase.EXECUTION
) {
    override fun handle(event: Event) {
        val fire = event.get<BlazingExploderFireEventAttribute>()!!
        val entity = event.get<EntitySourceEventAttribute>()?.entity

        val loc = fire.location.clone()
        loc.add(loc.direction.multiply(2.0))
        var runs = 8
        GearyCorePlugin.server.scheduler.runTaskTimer(GearyCorePlugin, { task ->
            loc.world?.createExplosion(loc, fire.strength * 2f,
                    false, fire.destroyBlocks, entity)
            move(loc, 2.0)
            if (--runs <= 0) task.cancel()
        }, 1, 4)
    }

    fun move(loc: Location, length: Double) {
        BlockIterator(loc, 0.0, ceil(length).toInt()).forEach { block ->
            if (!block.isPassable) {
                block.rayTrace(loc, loc.direction, length, FluidCollisionMode.NEVER)?.hitPosition?.let {
                    loc.add(it.subtract(loc.toVector()))
                    return
                }
            }
        }
        loc.add(loc.direction.multiply(length))
    }
}
