package io.github.paul1365972.gearycore.events

import io.github.paul1365972.geary.event.attributes.EventAttribute
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

data class EntityEventAttribute(
        var entity: Entity
) : EventAttribute

data class ItemEventAttribute(
        var itemStack: ItemStack
) : EventAttribute

data class PlayerOwnedAttribute(
        val player: Player
) : EventAttribute

class TickEventAttribute : EventAttribute
