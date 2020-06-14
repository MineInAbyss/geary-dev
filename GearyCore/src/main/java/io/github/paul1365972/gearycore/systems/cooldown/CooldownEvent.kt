package io.github.paul1365972.gearycore.systems.cooldown

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.attributes.EventAttribute
import io.github.paul1365972.geary.event.attributes.EventAttributeFamily
import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPhase
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.events.ItemEventAttribute
import kotlin.math.roundToLong

data class CooldownEventAttribute(
        var cooldown: Int
) : EventAttribute


class CooldownApplier : EventListener(GearyCorePlugin,
        EventAttributeFamily(setOf(CooldownEventAttribute::class.java, ItemEventAttribute::class.java)),
        EventPhase.EXECUTION) {
    override fun handle(event: Event) {
        val item = event.get<ItemEventAttribute>()!!.itemStack
        val cooldown = event.get<CooldownEventAttribute>()!!.cooldown
        item.cooldownComponent?.let {
            //TODO
            it.nextUse = (System.currentTimeMillis() / 1000.0 * 20 + cooldown).roundToLong()
            item.cooldownComponent = it
        }
    }
}
