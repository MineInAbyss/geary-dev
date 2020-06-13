package io.github.paul1365972.geary.event.listener

import io.github.paul1365972.geary.event.EventComponent

data class EventComponentFamily(
        val requiredTypes: Set<Class<out EventComponent>>,
        val disallowedTypes: Set<Class<out EventComponent>> = setOf()
) {
    companion object {
        val ALL: EventComponentFamily = EventComponentFamily(setOf(), setOf())
    }

    fun matches(types: Collection<Class<out EventComponent>>): Boolean {
        return requiredTypes.all(types::contains) && disallowedTypes.none(types::contains)
    }

    fun matches(accessor: (Class<out EventComponent>) -> Any?): Boolean {
        return requiredTypes.all { accessor(it) != null } && disallowedTypes.all { accessor(it) == null }
    }
}
