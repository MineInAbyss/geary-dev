package io.github.paul1365972.geary.event.listener

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.attributes.EventAttributeFamily
import org.bukkit.plugin.Plugin

abstract class EventListener(
        val plugin: Plugin,
        val family: EventAttributeFamily,
        val phase: EventPhase = EventPhase.EXECUTION,
        val priority: EventPriority = EventPriority.DEFAULT,
        val ignoreCancelled: Boolean = true
) {
    abstract fun handle(event: Event)
}
