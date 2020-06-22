package io.github.paul1365972.gearycore.events

import io.github.paul1365972.geary.event.attributes.EventAttribute
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack

class TickEventAttribute : EventAttribute

data class ItemSourceEventAttribute(
        var itemStack: ItemStack
) : EventAttribute

data class EntitySourceEventAttribute(
        var entity: Entity
) : EventAttribute

data class EntityTargetEventAttribute(
        var entity: Entity
) : EventAttribute

data class LocationSourceEventAttribute(
        var location: Location
) : EventAttribute

data class LocationTargetEventAttribute(
        var location: Location
) : EventAttribute

data class FaceTargetEventAttribute(
        var blockFace: BlockFace
) : EventAttribute
