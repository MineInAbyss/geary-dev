package com.mineinabyss.geary

import com.mineinabyss.geary.event.GearyEventManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.PluginDisableEvent

class PluginDisableListener(private val geary: Geary) : Listener {
    @EventHandler
    fun onPluginDisable(event: PluginDisableEvent) {
        GearyEventManager.unregister(event.plugin)
        geary.removeSystems(event.plugin)
    }
}
