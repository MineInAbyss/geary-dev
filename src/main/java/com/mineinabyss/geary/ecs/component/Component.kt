package com.mineinabyss.geary.ecs.component

import io.github.paul1365972.story.key.DataKey

interface Component<SELF : Component<SELF>> {
    val dataKey: DataKey<SELF>

    fun merge(other: SELF): SELF = other
}
