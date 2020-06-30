package io.github.paul1365972.gearycore.systems.climbing

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.story.access.access
import io.github.paul1365972.story.key.KsxDataKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.bukkit.entity.Entity

object ClimbingKey : KsxDataKey<ClimbingComponent>(
        GearyCorePlugin,
        "climbing",
        { it.copy() },
        ClimbingComponent.serializer()
)

@Serializable
data class ClimbingComponent(
        var oldFlySpeed: Float? = null,
        @Transient
        var sources: MutableMap<String, Float> = mutableMapOf()
) : Component<ClimbingComponent> {
    override fun key() = ClimbingKey
}

val Entity.climbingComponent get() = access(ClimbingKey)
