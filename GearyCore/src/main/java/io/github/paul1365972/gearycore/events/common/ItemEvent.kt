package io.github.paul1365972.gearycore.events.common

import io.github.paul1365972.geary.ecs.ItemEntity
import io.github.paul1365972.geary.event.EventI

interface ItemEventI : EventI {
    var item: ItemEntity
}

class ItemEvent(
        override var item: ItemEntity,
        override var cancelled: Boolean = false
) : ItemEventI
