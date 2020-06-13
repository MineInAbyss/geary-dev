package io.github.paul1365972.geary.event

interface EventComponent

data class CancelledEventComponent(
        var cancelled: Boolean
) : EventComponent
