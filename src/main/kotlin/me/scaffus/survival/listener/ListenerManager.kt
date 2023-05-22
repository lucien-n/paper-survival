package me.scaffus.survival.listener

import me.scaffus.survival.listener.listeners.PlayerJoinQuitListener
import me.scaffus.survival.Survival
import org.bukkit.Bukkit

class ListenerManager(private val plugin: Survival) {
    fun register() {
        plugin.logger.info("Registering listeners")
        Bukkit.getPluginManager().registerEvents(PlayerJoinQuitListener(plugin), plugin)
    }
}