package io.github.paul1365972.gearycore

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.EventListener
import io.github.paul1365972.story.StoryService
import io.github.paul1365972.story.key.CborDataKey
import kotlinx.serialization.Serializable
import org.bukkit.event.EventPriority
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


class DurabilityUseEvent(
        var itemStack: ItemStack,
        var amount: Int
) : Event()

class DurabilityListener : EventListener<DurabilityUseEvent>(GearyCore, priority = EventPriority.MONITOR) {
    override fun handle(event: DurabilityUseEvent) {
        event.itemStack.durabilityComponent?.apply {
            durability -= event.amount
            if (durability <= 0) {
                event.itemStack.amount = 0
            } else {
                val meta = event.itemStack.itemMeta
                if (meta is Damageable) {
                    val percentile = 1.0 * durability / maxDurability
                    meta.damage = (event.itemStack.type.maxDurability * percentile).roundToInt()
                    event.itemStack.itemMeta = meta
                }
            }
        }
    }
}
