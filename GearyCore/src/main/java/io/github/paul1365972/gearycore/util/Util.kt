package io.github.paul1365972.gearycore.util

import org.bukkit.FluidCollisionMode
import org.bukkit.Location
import org.bukkit.util.BlockIterator
import kotlin.math.ceil

fun Location.move(length: Double) {
    BlockIterator(this, 0.0, ceil(length).toInt()).forEach { block ->
        if (!block.isPassable) {
            block.rayTrace(this, this.direction, length, FluidCollisionMode.NEVER)?.hitPosition?.let {
                this.add(it.subtract(this.toVector()))
                return
            }
        }
    }
    this.add(this.direction.multiply(length))
}
