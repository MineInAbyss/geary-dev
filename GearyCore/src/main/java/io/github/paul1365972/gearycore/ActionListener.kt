package io.github.paul1365972.gearycore

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.gearycore.events.EntityEventAttribute
import io.github.paul1365972.gearycore.events.ItemEventAttribute
import io.github.paul1365972.gearycore.events.PlayerOwnedAttribute
import io.github.paul1365972.gearycore.events.UseEventAttribute
import io.github.paul1365972.gearycore.systems.blazereap.BlazingExploderComponent
import io.github.paul1365972.gearycore.systems.blazereap.blazingExploderComponent
import io.github.paul1365972.gearycore.systems.durability.DurabilityComponent
import io.github.paul1365972.gearycore.systems.durability.durabilityComponent
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class ActionListener : Listener {

    @EventHandler
    fun onClick(event: PlayerInteractEvent) {
        if (event.hand == EquipmentSlot.HAND) {
            when (event.action) {
                Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK -> {
                    Event(PlayerOwnedAttribute(event.player), EntityEventAttribute(event.player), UseEventAttribute()).apply {
                        event.item?.let { this += ItemEventAttribute(it) }
                    }.call()
                }
                else -> Unit
            }
        }
        event.item?.let {
            if (event.clickedBlock?.type == Material.DIAMOND_BLOCK) {
                it.blazingExploderComponent = BlazingExploderComponent(1f)
                it.durabilityComponent = DurabilityComponent(60, 60)
                event.player.sendMessage("Applied")
            }
        }
    }
}
