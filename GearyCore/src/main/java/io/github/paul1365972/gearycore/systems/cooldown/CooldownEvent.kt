package io.github.paul1365972.gearycore.systems.cooldown

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.attributes.EventAttribute
import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPhase
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.events.ItemSourceEventAttribute
import kotlin.math.roundToLong

data class UseCooldownEventAttribute(
        var cooldown: Int
) : EventAttribute


class CooldownCanceller : EventListener(
        GearyCorePlugin,
        EventPhase.INCUBATION
) {
    override fun handle(event: Event) = event.where<UseCooldownEventAttribute, ItemSourceEventAttribute> { _, (item) ->
        item.cooldownComponent.modify {
            nextUse = currentTicks() + cooldown
            event.cancelled = true
        }
    }
}

fun currentTicks() = (System.currentTimeMillis() / 1000.0 * 20).roundToLong()
