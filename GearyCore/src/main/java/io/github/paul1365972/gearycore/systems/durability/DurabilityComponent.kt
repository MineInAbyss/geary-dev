package io.github.paul1365972.gearycore.systems.durability

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.story.access.access
import io.github.paul1365972.story.key.KsxDataKey
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import org.bukkit.inventory.ItemStack

object DurabilityKey : KsxDataKey<DurabilityComponent>(
        GearyCorePlugin,
        "durability",
        { it.copy() },
        DurabilityComponent.serializer()
)

@Serializable
data class DurabilityComponent(
        var maxDurability: Int,
        @Required var durability: Int = maxDurability
) : Component<DurabilityComponent> {
    override fun key() = DurabilityKey
}


val ItemStack.durabilityComponent get() = access(DurabilityKey)
