package com.mineinabyss.geary

import com.mineinabyss.geary.ecs.engines.SimpleEngine
import com.mineinabyss.geary.ecs.system.GearySystem
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin

internal lateinit var PLUGIN: Geary

class Geary : JavaPlugin(), GearyService {
    private val engine = SimpleEngine()
    private val pluginSystemsMap = mutableMapOf<Plugin, MutableSet<GearySystem>>()

    override fun onLoad() {
        PLUGIN = this
    }

    override fun onEnable() {
        server.scheduler.scheduleSyncRepeatingTask(this, { engine.update() }, 1, 1)
        server.pluginManager.registerEvents(PluginDisableListener(this), this)
        server.servicesManager.register(GearyService::class.java, this, this, ServicePriority.Normal)
    }

    override fun onDisable() {}

    override fun addSystems(plugin: Plugin, vararg systems: GearySystem) {
        pluginSystemsMap.computeIfAbsent(plugin) { mutableSetOf() }.addAll(systems)
        systems.forEach { engine.addSystem(it) }
    }

    override fun removeSystem(plugin: Plugin, vararg systems: GearySystem) {
        pluginSystemsMap[plugin]?.removeAll(systems)
        systems.forEach { engine.removeSystem(it) }
    }

    override fun removeSystems(plugin: Plugin) {
        pluginSystemsMap.remove(plugin)?.forEach { engine.removeSystem(it) }
    }
}
