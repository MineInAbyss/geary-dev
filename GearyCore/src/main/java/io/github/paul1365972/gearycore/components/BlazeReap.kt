package io.github.paul1365972.gearycore.components

import io.github.paul1365972.geary.ecs.Component
import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.geary.event.EventComponent
import io.github.paul1365972.geary.event.listener.EventComponentFamily
import io.github.paul1365972.geary.event.listener.EventListener
import io.github.paul1365972.geary.event.listener.EventPriority
import io.github.paul1365972.gearycore.GearyCore
import io.github.paul1365972.gearycore.events.ItemEventComponent
import io.github.paul1365972.gearycore.events.PlayerOwnedComponent
import io.github.paul1365972.gearycore.events.UseEventComponent
import io.github.paul1365972.story.StoryService
import io.github.paul1365972.story.key.CborDataKey
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.inventory.ItemStack

object BlazingExploderKey : CborDataKey<BlazingExploderComponent>(GearyCore.INSTANCE, "blaze_exploder", BlazingExploderComponent.serializer()) {
    override fun copy(value: BlazingExploderComponent) = value.copy()
}

@Serializable
data class BlazingExploderComponent(
        var strength: Float = 1f
) : Component<BlazingExploderComponent>

var ItemStack.blazingExploderComponent: BlazingExploderComponent?
    get() = StoryService.itemStore.get(BlazingExploderKey, this)
    set(value) = StoryService.itemStore.set(BlazingExploderKey, this, value)


data class BlazingExploderFireEventComponent(
        var origin: Location,
        var strength: Float
) : EventComponent

class BlazingExploderFireListener : EventListener(GearyCore.INSTANCE,
        EventComponentFamily(setOf(BlazingExploderFireEventComponent::class.java)),
        priority = EventPriority.EXECUTION) {
    override fun handle(event: Event) {
        val fire = event.get<BlazingExploderFireEventComponent>()!!
        fire.origin.world?.createExplosion(fire.origin, fire.strength * 2.5f, false)
    }
}

class BlazingExploderUseListener : EventListener(GearyCore.INSTANCE,
        EventComponentFamily(setOf(UseEventComponent::class.java, ItemEventComponent::class.java, PlayerOwnedComponent::class.java)),
        priority = EventPriority.EXECUTION) {
    override fun handle(event: Event) {
        val item = event.get<ItemEventComponent>()!!
        val entity = event.get<PlayerOwnedComponent>()!!

        item.itemStack.blazingExploderComponent?.let { blazingExploder ->
            Event(BlazingExploderFireEventComponent(entity.player.eyeLocation, blazingExploder.strength)).call()
        }
    }
}
