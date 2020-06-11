package com.mineinabyss.geary.event

import org.bukkit.event.EventPriority
import org.bukkit.plugin.Plugin
import java.util.*
import java.util.stream.Stream

object GearyEventManager {
    private val classListenerMap = mutableMapOf<Class<out Event>, PriorityQueue<EventListener<*>>>()

    @Suppress("UNCHECKED_CAST")
    fun <T : Event> call(event: T): Boolean {
        classListenerMap[event.javaClass]?.forEach {
            if (!(it.ignoreCancelled && event.cancelled))
                (it as EventListener<T>).handler(event)
        }
        return !event.cancelled
    }

    fun <T : Event> register(clazz: Class<T>, listener: EventListener<T>) {
        Stream.iterate<Class<*>>(clazz, { clazz != Event::class.java }, { clazz.superclass }).forEach {
            classListenerMap.computeIfAbsent(clazz) { PriorityQueue() } += listener
        }
    }

    @JvmOverloads
    fun <T : Event> register(plugin: Plugin, clazz: Class<T>, listener: (T) -> Unit, ignoreCancelled: Boolean = true, priority: EventPriority = EventPriority.NORMAL) {
        register(clazz, EventListener(plugin, listener, ignoreCancelled, priority))
    }

    inline fun <reified T : Event> register(plugin: Plugin, noinline listener: (T) -> Unit, ignoreCancelled: Boolean = true, priority: EventPriority = EventPriority.NORMAL) {
        register(T::class.java, EventListener(plugin, listener, ignoreCancelled, priority))
    }

    fun unregister(plugin: Plugin) {
        classListenerMap.values.removeIf { queue ->
            queue.removeIf { it.plugin == plugin }
            queue.isEmpty()
        }
    }
}
