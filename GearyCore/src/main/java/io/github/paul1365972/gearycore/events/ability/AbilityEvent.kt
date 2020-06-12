package io.github.paul1365972.gearycore.events.ability

import io.github.paul1365972.geary.ecs.ItemEntity
import io.github.paul1365972.gearycore.events.common.EntityEventI
import io.github.paul1365972.gearycore.events.common.ItemEventI

interface AbilityEventI : EntityEventI, ItemEventI

class AbilityEvent(
        override var item: ItemEntity,
        override var strength: Float,
        override var cancelled: Boolean
) : AbilityStrengthEventI
