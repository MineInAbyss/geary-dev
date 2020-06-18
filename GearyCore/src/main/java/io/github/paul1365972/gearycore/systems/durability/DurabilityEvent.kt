package io.github.paul1365972.gearycore.systems.durability

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.attributes.EventAttribute
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
        EventPhase.EXECUTION
) {
    override fun handle(event: Event) = event.where<DurabilityUseEventAttribute, ItemSourceEventAttribute> { (durabilityUsage), (item) ->
        item.durabilityComponent.modify {
            val meta = item.itemMeta
            if (meta is Damageable) {
                //val remaining = 1.0 * durability / maxDurability
                //meta.damage = (item.type.maxDurability * (1 - remaining) + 0.5).roundToInt()
                val ratio = 1.0 * durabilityUsage / maxDurability
                meta.damage += (item.type.maxDurability * ratio + 0.5).roundToInt()
                if (meta.damage >= item.type.maxDurability) {
                    item.amount--
                    meta.damage = 0
                }
                item.itemMeta = meta
            } else {
                durability = max(0, durability - durabilityUsage)
                if (durability <= 0) {
                    item.amount--
                    durability = maxDurability
                }
            }
        }
    }
}
