package io.github.paul1365972.gearycore.systems.durability

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.attributes.EventAttribute
import io.github.paul1365972.geary.event.attributes.EventAttributeFamily
import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPhase
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.events.ItemSourceEventAttribute
import org.bukkit.inventory.meta.Damageable
import java.lang.Integer.max
import kotlin.math.roundToInt

data class DurabilityUseEventAttribute(
        var durabilityUsage: Int = 1
) : EventAttribute


class DurabilityItemDegrader : EventListener(
        GearyCorePlugin,
        EventAttributeFamily(setOf(ItemSourceEventAttribute::class.java, DurabilityUseEventAttribute::class.java)),
        EventPhase.EXECUTION
) {
    override fun handle(event: Event) {
        val itemComponent = event.get<ItemSourceEventAttribute>()!!
        val item = itemComponent.itemStack

        item.durabilityComponent.modify {
            val durabilityUseComponent = event.get<DurabilityUseEventAttribute>()!!
            val meta = item.itemMeta
            if (meta is Damageable) {
                //meta.damage = ((item.type.maxDurability) * (1 - remaining) + 0.5).roundToInt()
                val ratio = 1.0 * durabilityUseComponent.durabilityUsage / maxDurability
                meta.damage += (item.type.maxDurability * ratio + 0.5).roundToInt()
                if (meta.damage >= item.type.maxDurability) {
                    item.amount--
                    meta.damage = 0
                }
                item.itemMeta = meta
            } else {
                durability = max(0, durability - durabilityUseComponent.durabilityUsage)
                if (durability <= 0) {
                    item.amount--
                    durability = maxDurability
                }
            }
        }
    }
}
