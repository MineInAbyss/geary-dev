package io.github.paul1365972.gearycore.systems.cooldown

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.attributes.EventAttribute
import io.github.paul1365972.geary.event.attributes.EventAttributeFamily
import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPhase
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.events.ItemEventAttribute
import io.github.paul1365972.gearycore.events.TickEventAttribute

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
            it.remaining = cooldown
            item.cooldownComponent = it
        }
    }
}


class CooldownReducer : EventListener(GearyCorePlugin,
        EventAttributeFamily(setOf(TickEventAttribute::class.java, ItemEventAttribute::class.java)),
        EventPhase.EXECUTION) {
    override fun handle(event: Event) {
        val item = event.get<ItemEventAttribute>()!!.itemStack
        item.cooldownComponent?.let {
            if (it.remaining > 0) {
                it.remaining--
                item.cooldownComponent = it
            }
        }
    }
}
