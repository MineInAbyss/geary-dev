package io.github.paul1365972.geary.event

class Event {

    constructor(components: Set<EventComponent>) {
        components.forEach { this += it }
    }

    constructor(vararg components: EventComponent) : this(components.toSet())

    private val components = mutableMapOf<Class<out EventComponent>, EventComponent>()

    fun getKeys(): Set<Class<out EventComponent>> = components.keys.toSet()

    fun getComponents(): Collection<EventComponent> = components.values.toList()

    @Suppress("UNCHECKED_CAST")
    fun <T : EventComponent> get(type: Class<T>): T? = components[type] as T?

    inline fun <reified T : EventComponent> get(): T? = get(T::class.java)

    fun <T : EventComponent> has(type: Class<T>): Boolean = components.containsKey(type)

    fun <T : EventComponent> ifPresent(type: Class<T>, function: (T) -> Unit) {
        get(type)?.let { function(it) }
    }

    inline fun <reified T : EventComponent> ifPresent(noinline function: (T) -> Unit) {
        ifPresent(T::class.java, function)
    }

    fun <T : EventComponent> add(type: Class<T>, component: T) {
        components[type] = component
    }

    operator fun <T : EventComponent> plusAssign(component: T) {
        add(component)
    }

    fun add(component: EventComponent) = add(component.javaClass, component)

    @Suppress("UNCHECKED_CAST")
    fun <T : EventComponent> remove(type: Class<T>): T? = components.remove(type) as T?

    inline fun <reified T : EventComponent> remove(): T? = remove(T::class.java)

    fun call() = GearyEventManager.call(this)
}
