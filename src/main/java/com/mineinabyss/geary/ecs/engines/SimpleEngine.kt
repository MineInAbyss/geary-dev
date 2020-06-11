package com.mineinabyss.geary.ecs.engines

import com.mineinabyss.geary.ecs.entity.GearyEntity
import com.mineinabyss.geary.ecs.system.GearySystem
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer
import kotlin.collections.HashMap

class SimpleEngine : GearyEngine {

    private val systems: MutableSet<GearySystem> = mutableSetOf()
    private val uuidEntityTracker: MutableMap<UUID, GearyEntity> = ConcurrentHashMap<UUID, GearyEntity>()

    override fun update() {

    }

    override fun getEntities() {
        chunk.tileEntities
    }

    override fun addSystem(system: GearySystem) {
        if (systems.add(system))
            system.onAttach()
    }

    override fun removeSystem(system: GearySystem) {
        if (systems.remove(system))
            system.onDetach()
    }

    override fun addEntity(entity: GearyEntity) {
        if (uuidEntityTracker.containsKey(entity.uuid))
            throw RuntimeException("Already added entity with uuid ${entity.uuid}")
        uuidEntityTracker[entity.uuid] = entity
    }

    override fun getEntity(uuid: UUID): GearyEntity? {
        return uuidEntityTracker[uuid]
    }

    override fun removeEntity(uuid: UUID): GearyEntity? {
        if (!uuidEntityTracker.containsKey(uuid))
            throw RuntimeException("Could not find entity with uuid $uuid")
        return uuidEntityTracker.remove(uuid)
    }
}
