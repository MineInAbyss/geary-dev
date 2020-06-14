package io.github.paul1365972.geary.event.listener

class EventPriority(
        val slot: Int
) : Comparable<EventPriority> {
    companion object {
        val FIRST = EventPriority(-100)
        val EARLIEST = EventPriority(-30)
        val EARLIER = EventPriority(-20)
        val EARLY = EventPriority(-10)
        val DEFAULT = EventPriority(0)
        val LATE = EventPriority(10)
        val LATER = EventPriority(20)
        val LATEST = EventPriority(30)
        val LAST = EventPriority(100)
    }

    override fun compareTo(other: EventPriority): Int = slot.compareTo(other.slot)
}