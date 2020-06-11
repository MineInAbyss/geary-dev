package com.mineinabyss.geary.ecs.system

import io.github.paul1365972.story.key.DataKey

data class ComponentFamily(
        val requiredTypes: Set<DataKey<*>>,
        val disallowedTypes: Set<DataKey<*>>
) {
    companion object {
        val ALL: ComponentFamily = ComponentFamily(setOf(), setOf())
    }

    fun matches(types: Collection<DataKey<*>>): Boolean {
        return requiredTypes.all(types::contains) && disallowedTypes.none(types::contains)
    }

    fun matches(accessor: (DataKey<*>) -> Any?): Boolean {
        return requiredTypes.all { accessor(it) != null } && disallowedTypes.all { accessor(it) == null }
    }
}
