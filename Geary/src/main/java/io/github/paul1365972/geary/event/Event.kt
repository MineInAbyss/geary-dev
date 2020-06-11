package io.github.paul1365972.geary.event

open class Event {
    var cancelled = false
        private set

    fun setCancelled() {
        cancelled = true
    }

    fun call(): Boolean = GearyEventManager.call(this)
}
