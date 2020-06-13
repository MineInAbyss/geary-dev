package io.github.paul1365972.geary.event

import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPriority
import org.bukkit.plugin.Plugin

object GearyEventManager {
    private val listeners = mutableListOf<EventListener>()
    private val listenerMap = mutableMapOf<EventPriority, MutableList<EventListener>>()

    fun call(event: Event) {
        EventPriority.values().forEach { priority ->
            listenerMap[priority]?.let { listenerGroup ->
                val left = listenerGroup.toMutableList()
                while (left.isNotEmpty()) {
                    val listener = left.lastOrNull {
                        it.family.matches(event.getKeys())
                                && it.ignoreCancelled == event.get<CancelledEventComponent>()?.cancelled ?: true
                    } ?: break
                    left.removeAt(left.lastIndex)
                    listener.handle(event)
                }
            }
        }
    }

    fun register(listener: EventListener) {
        listeners.add(listener)
        listenerMap.computeIfAbsent(listener.priority) { mutableListOf() } += listener
    }

    fun unregister(plugin: Plugin) {
        listeners.removeIf { it.plugin == plugin }
        listenerMap.values.removeIf { listenerGroup ->
            listenerGroup.removeIf { it.plugin == plugin } && listenerGroup.isEmpty()
        }
    }
}
