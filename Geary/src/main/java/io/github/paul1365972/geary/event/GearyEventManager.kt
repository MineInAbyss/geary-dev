package io.github.paul1365972.geary.event

import org.bukkit.plugin.Plugin
import java.util.*
import java.util.stream.Stream

object GearyEventManager {
    private val classListenerMap = mutableMapOf<Class<out Event>, PriorityQueue<EventListener<*>>>()

    @Suppress("UNCHECKED_CAST")
    fun <T : Event> call(event: T): Boolean {
        classListenerMap[event.javaClass]?.forEach {
            if (!(it.ignoreCancelled && event.cancelled))
                (it as EventListener<T>).handle(event)
        }
        return !event.cancelled
    }

    fun <T : Event> register(clazz: Class<T>, listener: EventListener<T>) {
        Stream.iterate<Class<*>>(clazz, { clazz != Event::class.java }, { clazz.superclass }).forEach {
            classListenerMap.computeIfAbsent(clazz) { PriorityQueue() } += listener
        }
    }

    inline fun <reified T : Event> register(listener: EventListener<T>) {
        register(T::class.java, listener)
    }

    fun unregister(plugin: Plugin) {
        classListenerMap.values.removeIf { queue ->
            queue.removeIf { it.plugin == plugin }
            queue.isEmpty()
        }
    }
}
