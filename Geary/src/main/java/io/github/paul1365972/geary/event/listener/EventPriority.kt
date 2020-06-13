package io.github.paul1365972.geary.event.listener

enum class EventPriority(val value: Int) {

    /**
     * Event modification stage!
     * Make any changes to the event.
     */
    PRE(0),

    /**
     * Event control stage!
     * Only add delegates or cancel the event.
     */
    PRE_MONITOR(1),

    /**
     * Execution stage, event will be executed
     * The event should't be modified anymore at this point.
     */
    EXECUTION(2),

    /**
     * Post event monitor stage
     * The event should't be modified anymore at this point.
     */
    POST_MONITOR(3),

}
