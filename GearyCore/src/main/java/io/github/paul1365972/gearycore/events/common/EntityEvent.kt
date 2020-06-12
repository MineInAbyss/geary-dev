package io.github.paul1365972.gearycore.events.common

import io.github.paul1365972.geary.event.EventI
import org.bukkit.entity.Entity


interface EntityEventI : EventI {
    var entity: Entity
}

class EntityEvent(
        override var entity: Entity,
        override var cancelled: Boolean
) : EntityEventI
