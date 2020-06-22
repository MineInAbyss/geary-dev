package io.github.paul1365972.gearycore.systems.rope

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.gearycore.systems.cooldown.CooldownKey
import io.github.paul1365972.story.StoryService
import io.github.paul1365972.story.access.InstanceAccess
import io.github.paul1365972.story.access.access
import io.github.paul1365972.story.key.CborDataKey
import kotlinx.serialization.Serializable
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack

object RopeKey : CborDataKey<RopeComponent>(
        GearyCorePlugin,
        "rope",
        RopeComponent.serializer()
) {
    override fun copy(value: RopeComponent) = value.copy()
}

@Serializable
data class RopeComponent(
        var length: Float = 10f
) : Component<RopeComponent> {
    override fun key() = RopeKey
}

val ItemStack.ropeComponent get() = access(RopeKey)

val Entity.ropeComponent get() = access(RopeKey)
