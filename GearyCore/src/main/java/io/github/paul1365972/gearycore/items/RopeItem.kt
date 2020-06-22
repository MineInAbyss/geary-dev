package io.github.paul1365972.gearycore.items

import io.github.paul1365972.gearycore.systems.rope.RopeComponent
import io.github.paul1365972.gearycore.systems.rope.ropeComponent
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object RopeItem {

    @JvmStatic
    @JvmOverloads
    fun create(
            itemStack: ItemStack = ItemStack(Material.LEAD)
    ): ItemStack {
        return itemStack.apply {
            itemMeta = itemMeta!!.apply {
                setDisplayName("${ChatColor.YELLOW}Rope")
            }
            ropeComponent.set(RopeComponent(5f))
        }
    }
}
