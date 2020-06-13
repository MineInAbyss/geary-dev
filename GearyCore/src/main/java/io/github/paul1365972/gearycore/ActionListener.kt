package io.github.paul1365972.gearycore

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.gearycore.components.BlazingExploderComponent
import io.github.paul1365972.gearycore.components.blazingExploderComponent
import io.github.paul1365972.gearycore.events.EntityEventComponent
import io.github.paul1365972.gearycore.events.ItemEventComponent
import io.github.paul1365972.gearycore.events.PlayerOwnedComponent
import io.github.paul1365972.gearycore.events.UseEventComponent
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
                    Event(PlayerOwnedComponent(event.player), EntityEventComponent(event.player), UseEventComponent()).apply {
                        event.item?.let { this += ItemEventComponent(it) }
                    }.call()
                }
                else -> Unit
            }
        }
        event.item?.let {
            if (event.clickedBlock?.type == Material.DIAMOND_BLOCK) {
                it.blazingExploderComponent = BlazingExploderComponent(1f)
                event.player.sendMessage("Applied")
            }
        }
    }
}
