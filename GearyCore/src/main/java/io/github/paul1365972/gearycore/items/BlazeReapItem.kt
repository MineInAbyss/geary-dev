package io.github.paul1365972.gearycore.items

import io.github.paul1365972.gearycore.systems.blazereap.BlazingExploderComponent
import io.github.paul1365972.gearycore.systems.blazereap.blazingExploderComponent
import io.github.paul1365972.gearycore.systems.cooldown.CooldownComponent
import io.github.paul1365972.gearycore.systems.cooldown.cooldownComponent
import io.github.paul1365972.gearycore.systems.durability.DurabilityComponent
import io.github.paul1365972.gearycore.systems.durability.durabilityComponent
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object BlazeReapItem {

    @JvmStatic
    @JvmOverloads
    fun create(
            itemStack: ItemStack = ItemStack(Material.DIAMOND_PICKAXE),
            explosionStrength: Float = 1.0f,
            durability: Int = 6,
            cooldown: Int = 20
    ): ItemStack {
        return itemStack.apply {
            itemMeta = itemMeta!!.apply {
                setDisplayName("${ChatColor.GOLD}Blazing Reap")
            }
            blazingExploderComponent.set(BlazingExploderComponent(explosionStrength))
            durabilityComponent.set(DurabilityComponent(durability))
            cooldownComponent.set(CooldownComponent(cooldown))
        }
    }
}
