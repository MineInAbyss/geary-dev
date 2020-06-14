package io.github.paul1365972.gearycore

import org.bukkit.plugin.Plugin

object GearyCorePlugin : Plugin by Holder.INSTANCE {
    object Holder {
        lateinit var INSTANCE: GearyCore
    }

    override fun equals(other: Any?) = Holder.INSTANCE == other
    override fun hashCode() = Holder.INSTANCE.hashCode()
    override fun toString() = Holder.INSTANCE.toString()
}
