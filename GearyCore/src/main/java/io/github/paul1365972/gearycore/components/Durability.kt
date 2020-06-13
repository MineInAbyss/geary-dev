package io.github.paul1365972.gearycore.components

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.EventComponent
import io.github.paul1365972.geary.event.listener.EventComponentFamily
import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPriority
import io.github.paul1365972.gearycore.GearyCore
import io.github.paul1365972.gearycore.events.ItemEventComponent
import io.github.paul1365972.story.StoryService
import io.github.paul1365972.story.key.CborDataKey
import kotlinx.serialization.Serializable
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import java.lang.Integer.max
import kotlin.math.roundToInt

object DurabilityKey : CborDataKey<DurabilityComponent>(GearyCore.INSTANCE, "durability", DurabilityComponent.serializer()) {
    override fun copy(value: DurabilityComponent) = value.copy()
}

@Serializable
data class DurabilityComponent(
        var durability: Int,
        var maxDurability: Int
) : Component<DurabilityComponent>

var ItemStack.durabilityComponent: DurabilityComponent?
    get() = StoryService.itemStore.get(DurabilityKey, this)
    set(value) = StoryService.itemStore.set(DurabilityKey, this, value)


data class DurabilityUseEventComponent(
        var durabilityUsage: Int
) : EventComponent


class DurabilityItemDegrader : EventListener(GearyCore.INSTANCE,
        EventComponentFamily(setOf(ItemEventComponent::class.java, DurabilityUseEventComponent::class.java)),
        priority = EventPriority.EXECUTION) {
    override fun handle(event: Event) {
        val itemComponent = event.get<ItemEventComponent>()!!
        val durabilityUseComponent = event.get<DurabilityUseEventComponent>()!!
        val item = itemComponent.itemStack

        item.durabilityComponent?.apply {
            durability = max(0, durability - durabilityUseComponent.durabilityUsage)
            if (durability <= 0) {
                item.amount = 0
            } else {
                val meta = item.itemMeta
                if (meta is Damageable) {
                    val remaining = 1.0 * durability / maxDurability
                    meta.damage = (item.type.maxDurability * (1 - remaining)).roundToInt()
                    item.itemMeta = meta
                }
            }
        }
    }
}
