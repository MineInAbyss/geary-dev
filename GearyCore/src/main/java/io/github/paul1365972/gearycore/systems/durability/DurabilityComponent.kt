package io.github.paul1365972.gearycore.systems.durability

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.systems.cooldown.CooldownKey
import io.github.paul1365972.story.StoryService
import io.github.paul1365972.story.access.InstanceAccess
import io.github.paul1365972.story.access.access
import io.github.paul1365972.story.key.CborDataKey
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import org.bukkit.inventory.ItemStack

object DurabilityKey : CborDataKey<DurabilityComponent>(
        GearyCorePlugin,
        "durability",
        DurabilityComponent.serializer()
) {
    override fun copy(value: DurabilityComponent) = value.copy()
}


@Serializable
data class DurabilityComponent(
        var maxDurability: Int,
        @Required var durability: Int = maxDurability
) : Component<DurabilityComponent> {
    override fun key() = DurabilityKey
}


val ItemStack.durabilityComponent get() = access(DurabilityKey)
