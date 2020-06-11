package com.mineinabyss.geary.ecs.entity

import com.mineinabyss.geary.ecs.component.Component
import io.github.paul1365972.story.key.DataKey
import java.util.*

interface GearyEntity {
    val uuid: UUID

    fun <T : Component<T>> getComponent(type: DataKey<T>): T?

    fun getComponents(): Collection<Component<*>>

    fun <T : Component<T>> hasComponent(type: DataKey<T>): Boolean

    fun <T : Component<T>> ifPresent(type: DataKey<T>, function: (T) -> Unit) {
        getComponent(type)?.let { function(it) }
    }

    fun <T : Component<T>> addComponent(component: T)

    fun <T : Component<T>> removeComponent(type: DataKey<T>): T
}
