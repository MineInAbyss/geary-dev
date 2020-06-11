package com.mineinabyss.geary.event

open class Event {
    var cancelled = false
        private set

    fun setCancelled() {
        cancelled = true
    }

    fun call(): Boolean = GearyEventManager.call(this)
}
