package io.github.paul1365972.gearycore.systems.rope

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.attributes.EventAttribute
import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPhase
import io.github.paul1365972.geary.event.listener.EventPriority
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.events.*
import io.github.paul1365972.gearycore.systems.cooldown.UseCooldownEventAttribute
import io.github.paul1365972.gearycore.systems.cooldown.cooldownComponent
import io.github.paul1365972.gearycore.systems.durability.DurabilityUseEventAttribute
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Particle.*
import org.bukkit.block.BlockFace
import org.bukkit.entity.AreaEffectCloud
import org.bukkit.util.Vector
import kotlin.math.ceil
import kotlin.math.roundToInt


data class RopeCreateEventAttribute(
        var length: Float
) : EventAttribute


class RopeUseListener : EventListener(
        GearyCorePlugin,
        EventPhase.INCUBATION,
        EventPriority.EARLIER
) {
    override fun handle(event: Event) = event.where<UseEventAttribute, ItemSourceEventAttribute, LocationTargetEventAttribute, FaceTargetEventAttribute> { _, (item), _, (face) ->
        item.ropeComponent.ifPresent { rope ->
            if (face in setOf(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST)) {
                event.remove<UseEventAttribute>()
                event.add(RopeCreateEventAttribute(rope.length))
                event.add(DurabilityUseEventAttribute())
                item.cooldownComponent.ifPresent {
                    event.add(UseCooldownEventAttribute(it.cooldown))
                }
            }
        }
    }
}


class RopeCreateListener : EventListener(
        GearyCorePlugin,
        EventPhase.EXECUTION
) {
    override fun handle(event: Event) = event.where<RopeCreateEventAttribute, LocationTargetEventAttribute, FaceTargetEventAttribute> { (length), (location), (face) ->
        val loc = location.clone().add(Vector(0.5, 1.0, 0.5)).add(face.direction.multiply(0.5))
        loc.world!!.spawn(loc, AreaEffectCloud::class.java) {
            it.duration = 60
            it.radius = 0f
            it.radiusPerTick = 0f
            it.setParticle(BLOCK_DUST, Material.AIR.createBlockData())
            //it.clearCustomEffects()
        }.apply {
            ropeComponent.set(RopeComponent(length))
        }
    }
}


class RopeClimbListener : EventListener(
        GearyCorePlugin,
        EventPhase.EXECUTION
) {
    override fun handle(event: Event) = event.where<TickEventAttribute, EntitySourceEventAttribute> { _, (entity) ->
        entity.ropeComponent.ifPresent { (length) ->
            val loc = entity.location
            val particlesPerBlock = 10
            val particles = ceil(particlesPerBlock * length).roundToInt()
            val delta = Vector(0f, -length / particles, 0f)
            repeat(particles + 1) {
                entity.world.spawnParticle(REDSTONE, loc, 1, DustOptions(Color.fromRGB(178, 78, 16), 0.5f))
                loc.add(delta)
            }
        }
    }
}
