package com.mineinabyss.geary.ecs.entity

import com.mineinabyss.geary.ecs.component.Component
import io.github.paul1365972.story.key.DataKey
import java.util.*

class SimpleEntity(
        override val uuid: UUID
) : GearyEntity {
    override fun <T : Component<T>> getComponent(type: DataKey<T>): T? {
        TODO("Not yet implemented")
    }

    override fun getComponents(): Collection<Component<*>> {
        TODO("Not yet implemented")
    }

    override fun <T : Component<T>> hasComponent(type: DataKey<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun <T : Component<T>> addComponent(component: T) {
        TODO("Not yet implemented")
    }

    override fun <T : Component<T>> removeComponent(type: DataKey<T>): T {
        TODO("Not yet implemented")
    }
}
