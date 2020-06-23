package io.github.paul1365972.gearycore.systems.climbing

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.gearycore.GearyCorePlugin
import io.github.paul1365972.story.access.access
import io.github.paul1365972.story.key.CborDataKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.bukkit.entity.Entity

object ClimbingKey : CborDataKey<ClimbingComponent>(
        GearyCorePlugin,
        "climbing",
        ClimbingComponent.serializer()
) {
    override fun copy(value: ClimbingComponent) = value.copy()
}

@Serializable
data class ClimbingComponent(
        var oldFlySpeed: Float = 1f,
        @Transient
        val backingOriginSpeedMap: MutableMap<String, Float> = mutableMapOf()
) : Component<ClimbingComponent>, MutableMap<String, Float> by backingOriginSpeedMap {
    override fun key() = ClimbingKey
}

val Entity.climbingComponent get() = access(ClimbingKey)
