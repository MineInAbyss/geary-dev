package io.github.paul1365972.geary.event.listener

import io.github.paul1365972.geary.event.Event
import org.bukkit.plugin.Plugin

abstract class EventListener(
        val plugin: Plugin,
        val phase: EventPhase = EventPhase.EXECUTION,
        val priority: EventPriority = EventPriority.DEFAULT,
        val ignoreCancelled: Boolean = true
) {

    /**
     * TODO Just return true for now
     */
    abstract fun handle(event: Event): Boolean
}
