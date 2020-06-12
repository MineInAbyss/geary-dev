package io.github.paul1365972.geary.event

enum class EventPriority(val value: Int) {

    /**
     * Raw event monitor stage
     * The event should't be modified at this point.
     */
    CARDINAL(0),

    /**
     * Event control stage
     * Make changes to the event or add delegates before other listeners receive the event
     */
    FIRST(1),

    /**
     * Default
     */
    DEFAULT(4),

    /**
     * Event monitor stage
     * The event should't be modified at this point.
     */
    LAST(7),

    /**
     * Event execution stage
     * The event should't be modified at this point.
     */
    EXECUTION(8),
}
