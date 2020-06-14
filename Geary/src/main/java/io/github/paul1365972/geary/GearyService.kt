package io.github.paul1365972.geary

import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

interface GearyService {
    fun forPotentiallyActiveEntities(consumer: (Entity) -> Unit)
    fun forPotentiallyActiveItems(consumer: (ItemStack, Inventory, Player) -> Unit)
}
