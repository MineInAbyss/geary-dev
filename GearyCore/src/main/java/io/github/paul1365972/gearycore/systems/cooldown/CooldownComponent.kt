package io.github.paul1365972.gearycore.systems.cooldown

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.systems.blazereap.BlazingExploderComponent
import io.github.paul1365972.story.StoryService
import io.github.paul1365972.story.key.CborDataKey
import kotlinx.serialization.Serializable
import org.bukkit.inventory.ItemStack

object CooldownKey : CborDataKey<CooldownComponent>(GearyCorePlugin, "cooldown", CooldownComponent.serializer()) {
    override fun copy(value: CooldownComponent) = value.copy()
}

@Serializable
data class CooldownComponent(
        var remaining: Int,
        var cooldown: Int
) : Component<BlazingExploderComponent>

var ItemStack.cooldownComponent: CooldownComponent?
    get() = StoryService.itemStore.get(CooldownKey, this)
    set(value) = StoryService.itemStore.set(CooldownKey, this, value)
