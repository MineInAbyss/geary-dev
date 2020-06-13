package io.github.paul1365972.geary.ecs

interface Component<SELF : Component<SELF>> {
    fun merge(other: SELF): SELF = other
}
