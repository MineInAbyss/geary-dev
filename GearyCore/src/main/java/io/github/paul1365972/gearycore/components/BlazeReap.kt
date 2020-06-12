package io.github.paul1365972.gearycore.components

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.geary.ecs.ItemEntity
import io.github.paul1365972.geary.event.EventListener
import io.github.paul1365972.geary.event.EventPriority
import io.github.paul1365972.gearycore.GearyCore
import io.github.paul1365972.gearycore.events.ability.AbilityStrengthEventI
import io.github.paul1365972.story.StoryService
import io.github.paul1365972.story.key.CborDataKey
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.inventory.ItemStack

@Serializable
data class BlazingExploderComponent(
        var strength: Float = 1f
) : Component<BlazingExploderComponent> {
    companion object : CborDataKey<BlazingExploderComponent>(GearyCore, "blaze_exploder", BlazingExploderComponent.serializer()) {
        override fun copy(value: BlazingExploderComponent) = value.copy()
    }

    override fun getDataKey() = Companion
}

var ItemStack.blazingExploderComponent: BlazingExploderComponent?
    get() = StoryService.itemStore.get(BlazingExploderComponent, this)
    set(value) = StoryService.itemStore.set(BlazingExploderComponent, this, value)


interface BlazingExploderUseEventI : DurabilityUseEventI

class BlazingExploderUseEvent(
        override var item: ItemEntity,
        override var durabilityUsage: Int,
        override var cancelled: Boolean = false
) : BlazingExploderUseEventI


interface BlazingExplosionEventI : AbilityStrengthEventI {
    var origin: Location
}

class BlazingExplosionEvent(
        override var item: ItemEntity,
        override var strength: Float,
        override var origin: Location,
        override var cancelled: Boolean = false
) : BlazingExplosionEventI


class BlazingExploderUseListener : EventListener<BlazingExploderUseEventI>(GearyCore, priority = EventPriority.EXECUTION) {
    override fun handle(event: BlazingExploderUseEventI) {
        val strength = event.item.itemStack.blazingExploderComponent?.strength ?: 1f

        BlazingExplosionEvent(event.item, strength, )
    }
}
