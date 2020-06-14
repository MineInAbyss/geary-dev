package io.github.paul1365972.gearycore

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.gearycore.events.EntitySourceEventAttribute
import io.github.paul1365972.gearycore.events.ItemEventAttribute
import io.github.paul1365972.gearycore.events.UseEventAttribute
import io.github.paul1365972.gearycore.systems.blazereap.BlazingExploderComponent
import io.github.paul1365972.gearycore.systems.blazereap.blazingExploderComponent
import io.github.paul1365972.gearycore.systems.cooldown.CooldownComponent
import io.github.paul1365972.gearycore.systems.cooldown.cooldownComponent
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
        if (event.item?.itemMeta?.persistentDataContainer?.isEmpty != false) return
        if (event.hand == EquipmentSlot.HAND && event.hasItem()) {
            if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
                val e = Event(UseEventAttribute(), ItemEventAttribute(event.item!!),
                        EntitySourceEventAttribute(event.player))
                e.call()
                if (!e.has<UseEventAttribute>()) {
                    event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY)
                    event.setUseItemInHand(org.bukkit.event.Event.Result.DENY)
                }
            }
        }
    }

    // TODO
    @EventHandler
    fun test(event: PlayerInteractEvent) {
        event.item?.let {
            if (event.clickedBlock?.type == Material.DIAMOND_BLOCK) {
                it.blazingExploderComponent = BlazingExploderComponent(1f)
                it.durabilityComponent = DurabilityComponent(60, 60)
                it.cooldownComponent = CooldownComponent(0, 50)
                event.player.sendMessage("Applied")
            }
        }
    }
}
