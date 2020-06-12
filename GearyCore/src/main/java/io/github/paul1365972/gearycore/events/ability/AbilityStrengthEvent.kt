package io.github.paul1365972.gearycore.events.ability

import io.github.paul1365972.geary.ecs.ItemEntity
import io.github.paul1365972.gearycore.events.common.ItemEventI

interface AbilityStrengthEventI : ItemEventI {
    var strength: Float
}

class AbilityStrengthEvent(
        override var item: ItemEntity,
        override var strength: Float,
        override var cancelled: Boolean
) : AbilityStrengthEventI
