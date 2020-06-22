package io.github.paul1365972.gearycore

import io.github.paul1365972.geary.event.Event
import io.github.paul1365972.gearycore.events.*
import io.github.paul1365972.gearycore.items.BlazeReapItem
import io.github.paul1365972.gearycore.items.RopeItem
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
                val gearyEvent = Event(UseEventAttribute(), ItemSourceEventAttribute(event.item!!),
                        EntitySourceEventAttribute(event.player), LocationSourceEventAttribute(event.player.eyeLocation))
                event.clickedBlock?.let {
                    gearyEvent += LocationTargetEventAttribute(it.location)
                    gearyEvent += FaceTargetEventAttribute(event.blockFace)
                }
                gearyEvent.call()
                if (!gearyEvent.has<UseEventAttribute>()) {
                    event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY)
                    event.setUseItemInHand(org.bukkit.event.Event.Result.DENY)
                }
            }
        }
    }

    // TODO
    @EventHandler
    fun test(event: PlayerInteractEvent) {
        if (event.clickedBlock?.type == Material.DIAMOND_BLOCK) {
            event.player.inventory.addItem(BlazeReapItem.create())
        }
        if (event.clickedBlock?.type == Material.IRON_BLOCK) {
            event.player.inventory.addItem(RopeItem.create())
        }
    }
}
