package io.github.paul1365972.gearycore.systems.durability

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.story.StoryService
import io.github.paul1365972.story.key.CborDataKey
import kotlinx.serialization.Serializable
import org.bukkit.inventory.ItemStack

object DurabilityKey : CborDataKey<DurabilityComponent>(GearyCorePlugin, "durability", DurabilityComponent.serializer()) {
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
