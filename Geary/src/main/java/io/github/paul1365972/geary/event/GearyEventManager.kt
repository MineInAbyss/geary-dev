package io.github.paul1365972.geary.event

import org.bukkit.plugin.Plugin
import java.util.*
import java.util.stream.Stream

object GearyEventManager {
    private val classListenerMap = mutableMapOf<Class<*>, PriorityQueue<EventListener<*>>>()

    @Suppress("UNCHECKED_CAST")
    fun <T : EventI> call(event: T): Boolean {
        classListenerMap[event.javaClass]?.forEach {
            if (!(it.ignoreCancelled && event.cancelled))
                (it as EventListener<T>).handle(event)
        }
        return !event.cancelled
    }

    fun <T : EventI> register(clazz: Class<T>, listener: EventListener<T>) {
        Stream.iterate<Class<*>>(clazz, { it.superclass != null }, { it.superclass })
                .flatMap { Arrays.stream(it.interfaces) }
                .distinct()
                .filter { EventI::class.java.isAssignableFrom(it) }
                .forEach {
                    classListenerMap.computeIfAbsent(it) { PriorityQueue() } += listener
                }
    }

    inline fun <reified T : EventI> register(listener: EventListener<T>) {
        register(T::class.java, listener)
    }

    fun unregister(plugin: Plugin) {
        classListenerMap.values.removeIf { queue ->
            queue.removeIf { it.plugin == plugin }
            queue.isEmpty()
        }
    }
}
