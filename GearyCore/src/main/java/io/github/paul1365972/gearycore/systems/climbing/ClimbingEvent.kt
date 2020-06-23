package io.github.paul1365972.gearycore.systems.climbing

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPhase
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.events.EntitySourceEventAttribute
import io.github.paul1365972.gearycore.events.TickEntityEventAttribute
import org.bukkit.entity.Player


class ClimbingListener : EventListener(
        GearyCorePlugin,
        EventPhase.EXECUTION
) {
    override fun handle(event: Event) = event.where<TickEntityEventAttribute, EntitySourceEventAttribute> { _, (entity) ->
        if (entity is Player) {
            entity.climbingComponent.modify {
                val speed = sources.values.max()
                if (speed != null) {
                    if (!entity.isFlying || entity.flySpeed < speed) {
                        oldFlySpeed = entity.flySpeed
                        entity.isFlying = true
                        entity.flySpeed = speed
                    }
                } else {
                    if (entity.isFlying) {
                        oldFlySpeed?.let {
                            entity.isFlying = false
                            entity.flySpeed = it
                            oldFlySpeed = null
                        }
                    }
                }
                sources.clear()
            }
        }
    }
}
