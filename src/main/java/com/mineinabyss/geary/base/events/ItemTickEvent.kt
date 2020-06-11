package com.mineinabyss.geary.base.events

import com.mineinabyss.geary.event.Event
import org.bukkit.inventory.ItemStack

class ItemTickEvent(
        val itemStack: ItemStack
) : Event()
