package io.github.paul1365972.geary.event

interface EventI {
    var cancelled: Boolean

    fun call(): Boolean = GearyEventManager.call(this)
}

data class Event(
        override var cancelled: Boolean = false
) : EventI
