package io.github.paul1365972.geary

import org.bukkit.plugin.Plugin

object GearyPlugin : Plugin by Holder.INSTANCE, GearyService by Holder.INSTANCE {
    object Holder {
        lateinit var INSTANCE: Geary
    }

    override fun equals(other: Any?) = Holder.INSTANCE == other
    override fun hashCode() = Holder.INSTANCE.hashCode()
    override fun toString() = Holder.INSTANCE.toString()
}
