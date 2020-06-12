package io.github.paul1365972.gearycore.events.tick

import io.github.paul1365972.geary.ecs.ItemEntity
import io.github.paul1365972.gearycore.events.common.ItemEventI

interface ItemTickEventI : ItemEventI

class ItemTickEvent(
        override var item: ItemEntity,
        override var cancelled: Boolean = false
) : ItemTickEventI
