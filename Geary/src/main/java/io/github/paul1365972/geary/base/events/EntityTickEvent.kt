package io.github.paul1365972.geary.base.events

import io.github.paul1365972.geary.event.Event
import org.bukkit.entity.Entity

class EntityTickEvent(
        val entity: Entity
) : Event()
