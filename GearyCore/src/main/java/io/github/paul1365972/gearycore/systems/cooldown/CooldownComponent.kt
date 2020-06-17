package io.github.paul1365972.gearycore.systems.cooldown

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.systems.blazereap.BlazingExploderComponent
import io.github.paul1365972.story.StoryService
import io.github.paul1365972.story.access.InstanceAccess
import io.github.paul1365972.story.key.CborDataKey
import kotlinx.serialization.Serializable
import org.bukkit.inventory.ItemStack

object CooldownKey : CborDataKey<CooldownComponent>(
        GearyCorePlugin,
        "cooldown",
        CooldownComponent.serializer()
) {
    override fun copy(value: CooldownComponent) = value.copy()
}

@Serializable
data class CooldownComponent(
        var nextUse: Long,
        var cooldown: Int
) : Component<BlazingExploderComponent>

val ItemStack.cooldownComponent: InstanceAccess<CooldownComponent, ItemStack>
    get() = InstanceAccess(StoryService.defaultItemStore, CooldownKey, this)
