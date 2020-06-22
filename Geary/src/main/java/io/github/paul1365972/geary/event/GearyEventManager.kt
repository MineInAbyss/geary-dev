package io.github.paul1365972.geary.event

import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPhase
import org.bukkit.plugin.Plugin
import java.util.*

object GearyEventManager {
    private val listeners = mutableListOf<EventListener>()
    private val listenerMap = mutableMapOf<EventPhase, PriorityQueue<EventListener>>()

    @JvmStatic
    fun call(event: Event) {
        listenerMap[EventPhase.INCUBATION]?.let { listenerGroup ->
            val left = listenerGroup.toMutableList()
            while (left.isNotEmpty()) {
                val index = left.indexOfLast {
                    !(it.ignoreCancelled && event.cancelled) && it.handle(event)
                }
                if (index < 0) break
                left.removeAt(index)
            }
        }
        (EventPhase.values().toMutableList() - EventPhase.INCUBATION).forEach { priority ->
            listenerMap[priority]?.forEach { listener ->
                if (!(listener.ignoreCancelled && event.cancelled))
                    listener.handle(event)
            }
        }
    }

    @JvmStatic
    fun register(listener: EventListener) {
        listeners.add(listener)
        listenerMap.computeIfAbsent(listener.phase) { PriorityQueue(compareBy { it.priority }) } += listener
    }

    @JvmStatic
    fun unregister(plugin: Plugin) {
        listeners.removeIf { it.plugin == plugin }
        listenerMap.values.removeIf { listenerGroup ->
            listenerGroup.removeIf { it.plugin == plugin } && listenerGroup.isEmpty()
        }
    }
}
