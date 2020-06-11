package com.mineinabyss.geary.event

import com.mineinabyss.geary.ecs.system.ComponentFamily
import org.bukkit.event.EventPriority
import org.bukkit.plugin.Plugin

class EventListener<T : Event>(
        val plugin: Plugin,
        val family: ComponentFamily,
        val handler: (T) -> Unit,
        val priority: EventPriority = EventPriority.NORMAL
) : Comparable<EventListener<*>> {
    override fun compareTo(other: EventListener<*>) = priority.slot.compareTo(other.priority.slot)
}
