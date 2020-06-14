package io.github.paul1365972.gearycore.events

import io.github.paul1365972.geary.event.attributes.EventAttribute
import org.bukkit.Location

class AbilityEventAttribute(
        var strength: Float
) : EventAttribute

class SourceLocationEventAttribute(
        var location: Location
) : EventAttribute

class TargetLocationEventAttribute(
        var location: Location
) : EventAttribute
