package io.github.paul1365972.geary.event.attributes

interface EventAttribute

data class CancelledEventAttribute(
        var cancelled: Boolean
) : EventAttribute
