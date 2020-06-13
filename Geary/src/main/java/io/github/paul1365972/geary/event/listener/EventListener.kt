package io.github.paul1365972.geary.event.listener

import io.github.paul1365972.geary.event.Event
import org.bukkit.plugin.Plugin

abstract class EventListener(
        val plugin: Plugin,
        val family: EventComponentFamily,
        val ignoreCancelled: Boolean = true,
        val priority: EventPriority = EventPriority.EXECUTION
) : Comparable<EventListener> {
    override fun compareTo(other: EventListener) = priority.value.compareTo(other.priority.value)

    abstract fun handle(event: Event)
}
