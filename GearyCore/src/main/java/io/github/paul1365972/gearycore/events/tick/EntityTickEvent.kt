package io.github.paul1365972.gearycore.events.tick

import io.github.paul1365972.gearycore.events.common.EntityEventI
import org.bukkit.entity.Entity

interface EntityTickEventI : EntityEventI

class EntityTickEvent(
        override var entity: Entity,
        override var cancelled: Boolean = false
) : EntityTickEventI
