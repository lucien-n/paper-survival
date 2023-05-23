package me.scaffus.survival.listener

import me.scaffus.survival.Survival
import org.bukkit.Bukkit
import org.bukkit.event.Listener

open class SListener(private val plugin: Survival, val name: String) : Listener {
    init {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }
}