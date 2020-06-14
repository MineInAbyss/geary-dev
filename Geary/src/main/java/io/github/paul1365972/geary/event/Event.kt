package io.github.paul1365972.geary.event

import io.github.paul1365972.geary.event.attributes.EventAttribute

class Event {

    constructor(attributes: Set<EventAttribute>) {
        attributes.forEach { this += it }
    }

    constructor(vararg attributes: EventAttribute) : this(attributes.toSet())

    private val attributes = mutableMapOf<Class<out EventAttribute>, EventAttribute>()

    fun getKeys(): Set<Class<out EventAttribute>> = attributes.keys.toSet()

    fun getAttributes(): Collection<EventAttribute> = attributes.values.toList()

    @Suppress("UNCHECKED_CAST")
    fun <T : EventAttribute> get(type: Class<T>): T? = attributes[type] as T?

    inline fun <reified T : EventAttribute> get(): T? = get(T::class.java)

    fun <T : EventAttribute> has(type: Class<T>): Boolean = attributes.containsKey(type)

    inline fun <reified T : EventAttribute> has(): Boolean = has(T::class.java)

    fun <T : EventAttribute> ifPresent(type: Class<T>, function: (T) -> Unit) {
        get(type)?.let { function(it) }
    }

    inline fun <reified T : EventAttribute> ifPresent(noinline function: (T) -> Unit) {
        ifPresent(T::class.java, function)
    }

    fun <T : EventAttribute> add(type: Class<T>, attribute: T) {
        attributes[type] = attribute
    }

    operator fun <T : EventAttribute> plusAssign(attribute: T) {
        add(attribute)
    }

    fun add(attribute: EventAttribute) = add(attribute.javaClass, attribute)

    @Suppress("UNCHECKED_CAST")
    fun <T : EventAttribute> remove(type: Class<T>): T? = attributes.remove(type) as T?

    inline fun <reified T : EventAttribute> remove(): T? = remove(T::class.java)

    fun call() = GearyEventManager.call(this)
}
