package com.mineinabyss.geary.event

import org.bukkit.event.EventPriority
import org.bukkit.plugin.Plugin

class EventListener<T : Event>(
        val plugin: Plugin,
        val handler: (T) -> Unit,
        val ignoreCancelled: Boolean = true,
        val priority: EventPriority = EventPriority.NORMAL
) : Comparable<EventListener<*>> {
    override fun compareTo(other: EventListener<*>) = priority.slot.compareTo(other.priority.slot)
}
