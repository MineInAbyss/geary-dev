package io.github.paul1365972.geary.event

import org.bukkit.event.EventPriority
import org.bukkit.plugin.Plugin

abstract class EventListener<T : Event>(
        val plugin: Plugin,
        val ignoreCancelled: Boolean = true,
        val priority: EventPriority = EventPriority.NORMAL
) : Comparable<EventListener<*>> {
    override fun compareTo(other: EventListener<*>) = priority.slot.compareTo(other.priority.slot)

    abstract fun handle(event: T)
}
