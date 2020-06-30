package io.github.paul1365972.gearycore.systems.blazereap

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.story.access.access
import io.github.paul1365972.story.key.KsxDataKey
import kotlinx.serialization.Serializable
import org.bukkit.inventory.ItemStack

object BlazingExploderKey : KsxDataKey<BlazingExploderComponent>(
        GearyCorePlugin,
        "blazing_exploder",
        { it.copy() },
        BlazingExploderComponent.serializer()
)

@Serializable
data class BlazingExploderComponent(
        var strength: Float = 1f,
        var destroyBlocks: Boolean = true
) : Component<BlazingExploderComponent> {
    override fun key() = BlazingExploderKey
}

val ItemStack.blazingExploderComponent get() = access(BlazingExploderKey)
