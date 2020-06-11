package com.mineinabyss.geary

import com.mineinabyss.geary.ecs.system.GearySystem
import org.bukkit.plugin.Plugin

interface GearyService {
    fun addSystems(plugin: Plugin, vararg systems: GearySystem)
    fun removeSystem(plugin: Plugin, vararg systems: GearySystem)
    fun removeSystems(plugin: Plugin)
}
