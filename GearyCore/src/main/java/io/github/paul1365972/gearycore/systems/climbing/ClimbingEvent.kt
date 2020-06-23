package io.github.paul1365972.gearycore.systems.climbing

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPhase
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.events.EntitySourceEventAttribute
import io.github.paul1365972.gearycore.events.TickEventAttribute
import org.bukkit.entity.Player


class ClimbingListener : EventListener(
        GearyCorePlugin,
        EventPhase.EXECUTION
) {
    override fun handle(event: Event) = event.where<TickEventAttribute, EntitySourceEventAttribute> { _, (entity) ->
        if (entity is Player) {
            entity.climbingComponent.ifPresent { climb ->
                val speed = climb.values.max()
                if (speed != null) {
                    if (!entity.isFlying || entity.flySpeed < speed) {
                        climb.oldFlySpeed = entity.flySpeed
                        entity.isFlying = true
                        entity.flySpeed = speed
                    }
                } else {
                    if (entity.isFlying) {
                        entity.isFlying = false
                        entity.flySpeed = climb.oldFlySpeed
                    }
                }
                climb.clear()
            }
        }
    }
}
