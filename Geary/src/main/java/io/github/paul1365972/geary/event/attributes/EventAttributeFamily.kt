package io.github.paul1365972.geary.event.attributes

data class EventAttributeFamily(
        val requiredTypes: Set<Class<out EventAttribute>>,
        val disallowedTypes: Set<Class<out EventAttribute>> = setOf()
) {
    companion object {
        val ALL: EventAttributeFamily = EventAttributeFamily(setOf(), setOf())
    }

    fun matches(types: Collection<Class<out EventAttribute>>): Boolean {
        return requiredTypes.all(types::contains) && disallowedTypes.none(types::contains)
    }

    fun matches(accessor: (Class<out EventAttribute>) -> Any?): Boolean {
        return requiredTypes.all { accessor(it) != null } && disallowedTypes.all { accessor(it) == null }
    }
}
