package io.github.paul1365972.gearycore.events

import io.github.paul1365972.geary.event.EventComponent
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

data class EntityEventComponent(
        var entity: Entity
) : EventComponent

data class ItemEventComponent(
        var itemStack: ItemStack
) : EventComponent

data class PlayerOwnedComponent(
        val player: Player
) : EventComponent

class TickEventComponent : EventComponent
