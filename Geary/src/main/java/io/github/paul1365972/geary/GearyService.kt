package io.github.paul1365972.geary

import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

private val INSTANCE by lazy { Bukkit.getServer().servicesManager.load(GearyService::class.java)!! }

interface GearyService : Plugin {
    companion object : @JvmStatic GearyService by INSTANCE {
        override fun equals(other: Any?) = INSTANCE == other
        override fun hashCode() = INSTANCE.hashCode()
        override fun toString() = INSTANCE.toString()
    }

    fun forPotentiallyActiveEntities(consumer: (Entity) -> Unit)
    fun forPotentiallyActiveItems(consumer: (ItemStack, Inventory, Player) -> Unit)
}
