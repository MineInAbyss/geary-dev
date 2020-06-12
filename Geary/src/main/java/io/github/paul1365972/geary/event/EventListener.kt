package io.github.paul1365972.geary.event

import org.bukkit.plugin.Plugin

abstract class EventListener<T : EventI>(
        val plugin: Plugin,
        val ignoreCancelled: Boolean = true,
        val priority: EventPriority = EventPriority.DEFAULT
) : Comparable<EventListener<*>> {
    override fun compareTo(other: EventListener<*>) = priority.value.compareTo(other.priority.value)

    abstract fun handle(event: T)
}
