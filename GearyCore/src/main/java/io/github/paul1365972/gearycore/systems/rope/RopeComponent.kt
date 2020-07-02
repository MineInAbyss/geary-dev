package io.github.paul1365972.gearycore.systems.rope

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.story.StoryService
import io.github.paul1365972.story.key.KsxDataKey
import kotlinx.serialization.Serializable
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack

object RopeItemKey : KsxDataKey<RopeComponent, ItemStack>(
        GearyCorePlugin,
        "rope",
        StoryService.defaultItemStore,
        { it.copy() },
        RopeComponent.serializer()
)

object RopeEntityKey : KsxDataKey<RopeComponent, Entity>(
        GearyCorePlugin,
        "rope",
        StoryService.defaultEntityStore,
        { it.copy() },
        RopeComponent.serializer()
)

@Serializable
data class RopeComponent(
        var length: Float = 10f
) : Component<RopeComponent> {
    override fun key() = RopeItemKey //TODO we are fked
}
