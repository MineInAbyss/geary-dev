package io.github.paul1365972.gearycore.systems.cooldown

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.story.StoryService
import io.github.paul1365972.story.key.KsxDataKey
import kotlinx.serialization.Serializable
import org.bukkit.inventory.ItemStack

object CooldownKey : KsxDataKey<CooldownComponent, ItemStack>(
        GearyCorePlugin,
        "cooldown",
        StoryService.defaultItemStore,
        { it.copy() },
        CooldownComponent.serializer()
)

@Serializable
data class CooldownComponent(
        var cooldown: Int,
        var nextUse: Long = 0
) : Component<CooldownComponent> {
    override fun key() = CooldownKey
}
