package com.mineinabyss.geary.ecs.system

import com.mineinabyss.geary.ecs.engines.GearyEngine
import com.mineinabyss.geary.ecs.entity.GearyEntity

abstract class GearySystem(
        val engine: GearyEngine,
        val componentFamily: ComponentFamily
) {
    abstract fun update(entities: Collection<GearyEntity>)

    abstract fun onAttach()

    abstract fun onDetach()
}
