package io.github.paul1365972.gearycore.components

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.geary.ecs.ItemEntity
import io.github.paul1365972.geary.event.EventListener
import io.github.paul1365972.geary.event.EventPriority
import io.github.paul1365972.gearycore.GearyCore
import io.github.paul1365972.gearycore.events.common.ItemEventI
import io.github.paul1365972.story.StoryService
import io.github.paul1365972.story.key.CborDataKey
import kotlinx.serialization.Serializable
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import kotlin.math.roundToInt

@Serializable
data class DurabilityComponent(
        var durability: Int,
        var maxDurability: Int
) : Component<DurabilityComponent> {
    companion object : CborDataKey<DurabilityComponent>(GearyCore, "durability", DurabilityComponent.serializer()) {
        override fun copy(value: DurabilityComponent) = value.copy()
    }

    override fun getDataKey() = Companion
}

var ItemStack.durabilityComponent: DurabilityComponent?
    get() = StoryService.itemStore.get(DurabilityComponent, this)
    set(value) = StoryService.itemStore.set(DurabilityComponent, this, value)


interface DurabilityUseEventI : ItemEventI {
    var durabilityUsage: Int
}

data class DurabilityUseEvent(
        override var item: ItemEntity,
        override var durabilityUsage: Int = 1,
        override var cancelled: Boolean = false
) : DurabilityUseEventI


class DurabilityListener : EventListener<DurabilityUseEvent>(GearyCore, priority = EventPriority.EXECUTION) {
    override fun handle(event: DurabilityUseEvent) {
        val (itemStack) = event.item
        itemStack.durabilityComponent?.apply {
            durability -= event.durabilityUsage
            if (durability <= 0) {
                itemStack.amount = 0
            } else {
                val meta = itemStack.itemMeta
                if (meta is Damageable) {
                    val remaining = 1.0 * durability / maxDurability
                    meta.damage = (itemStack.type.maxDurability * (1 - remaining)).roundToInt()
                    itemStack.itemMeta = meta
                }
            }
        }
    }
}
