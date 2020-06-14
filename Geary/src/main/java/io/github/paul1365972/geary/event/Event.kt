package io.github.paul1365972.geary.event

import io.github.paul1365972.geary.GearyPlugin
import io.github.paul1365972.geary.event.attributes.EventAttribute

class Event {

    constructor(attributes: Set<EventAttribute>) {
        attributes.forEach { this += it }
    }

    constructor(vararg attributes: EventAttribute) : this(attributes.toSet())

    val attributes = mutableMapOf<Class<out EventAttribute>, EventAttribute>()

    fun call() = GearyEventManager.call(this)

    @Suppress("UNCHECKED_CAST")
    fun <T : EventAttribute> get(type: Class<T>): T? = attributes[type] as T?

    fun <T : EventAttribute> has(type: Class<T>): Boolean = attributes.containsKey(type)

    fun <T : EventAttribute> ifPresent(type: Class<T>, function: (T) -> Unit) {
        get(type)?.let { function(it) }
    }

    fun <T : EventAttribute> add(type: Class<T>, attribute: T) {
        if (attributes.containsKey(type)) GearyPlugin.logger.warning("Replaced EventAttribute(${type.name}) with Event.add()")
        attributes[type] = attribute
    }

    fun add(attribute: EventAttribute) = add(attribute.javaClass, attribute)

    operator fun <T : EventAttribute> plusAssign(attribute: T) = add(attribute)

    @Suppress("UNCHECKED_CAST")
    fun <T : EventAttribute> remove(type: Class<T>): T? = attributes.remove(type) as T?

    inline fun <reified T : EventAttribute> get(): T? = get(T::class.java)

    inline fun <reified T : EventAttribute> has(): Boolean = has(T::class.java)

    inline fun <reified T : EventAttribute> ifPresent(noinline function: (T) -> Unit) {
        ifPresent(T::class.java, function)
    }

    inline fun <reified T : EventAttribute> remove(): T? = remove(T::class.java)

    inline fun <reified T : EventAttribute> modify(crossinline defaultValue: () -> T, crossinline modifyFunction: (T) -> Unit): T? {
        return attributes.compute(T::class.java) { _, v ->
            v?.apply { modifyFunction(v as T) } ?: defaultValue()
        } as T?
    }

    inline fun <reified T : EventAttribute> merge(value: T, crossinline mergeFunction: T.(T) -> Unit): T? {
        return attributes.merge(T::class.java, value) { v1, v2 ->
            mergeFunction(v2 as T, v1 as T)
            v2
        } as T?
    }
}
