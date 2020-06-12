package io.github.paul1365972.geary.ecs

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

open class ItemEntity(
        val itemStack: ItemStack
) {
    operator fun component1() = this.itemStack
}


open class PlayerItemEntity(
        itemStack: ItemStack,
        val inventory: Inventory,
        val player: Player
) : ItemEntity(itemStack) {
    operator fun component2() = this.inventory
    operator fun component3() = this.player
}
