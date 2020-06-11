package com.mineinabyss.geary.ecs.engines

import com.mineinabyss.geary.ecs.entity.GearyEntity
import com.mineinabyss.geary.ecs.system.GearySystem
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.function.BiConsumer

interface GearyEngine {

    fun update()

    fun addSystem(system: GearySystem)
    fun removeSystem(system: GearySystem)

    fun addEntity(entity: GearyEntity)
    fun getEntity(uuid: UUID): GearyEntity?
    fun removeEntity(uuid: UUID): GearyEntity?
}
