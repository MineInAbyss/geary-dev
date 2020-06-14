package io.github.paul1365972.geary.event.listener

enum class EventPhase(val value: Int) {

    /**
     * Event incubation stage
     *
     * Make any changes to the event; add, remove, modify components
     */
    INCUBATION(0),

    /**
     * Event modification stage
     *
     * Only change properties of components; e.g. canceling the event
     */
    MODIFICATION(1),

    /**
     * Pre event monitor stage
     *
     * The event should't be modified anymore at this point
     */
    PRE(2),

    /**
     * Event execution stage
     *
     * The event should't be modified anymore at this point
     */
    EXECUTION(3),

    /**
     * Post event monitor stage
     *
     * The event should't be modified anymore at this point
     */
    POST(4),

}
