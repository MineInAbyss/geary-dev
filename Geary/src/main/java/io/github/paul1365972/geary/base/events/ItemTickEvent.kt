package io.github.paul1365972.geary.base.events

import io.github.paul1365972.geary.event.Event
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class ItemTickEvent(
        val itemStack: ItemStack,
        val inventory: Inventory,
        val player: Player
) : Event()
