package io.github.paul1365972.geary.ecs

import io.github.paul1365972.story.key.DataKey

interface Component<SELF : Component<SELF>> {
    fun getDataKey(): DataKey<SELF>

    fun merge(other: SELF): SELF = other
}
