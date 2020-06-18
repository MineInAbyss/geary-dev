package io.github.paul1365972.gearycore.systems.cooldown

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.attributes.EventAttribute
import io.github.paul1365972.geary.event.attributes.EventAttributeFamily
import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPhase
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.events.ItemSourceEventAttribute
import kotlin.math.roundToLong

data class ApplyCooldownEventAttribute(
        var cooldown: Int
) : EventAttribute


class CooldownApplier : EventListener(
        GearyCorePlugin,
        EventAttributeFamily(setOf(ApplyCooldownEventAttribute::class.java, ItemSourceEventAttribute::class.java)),
        EventPhase.EXECUTION
) {
    override fun handle(event: Event) {
        val item = event.get<ItemSourceEventAttribute>()!!.itemStack
        val cooldown = event.get<ApplyCooldownEventAttribute>()!!.cooldown
        item.cooldownComponent.modify {
            nextUse = currentTicks() + cooldown
        }
    }
}

fun currentTicks() = (System.currentTimeMillis() / 1000.0 * 20).roundToLong()
