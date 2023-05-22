package me.scaffus.survival.listener.listeners

import me.scaffus.survival.Survival
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerJoinQuitListener(private val plugin: Survival) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val p = event.player
        plugin.db.createPlayer(p)
        plugin.playerManager.loadPlayer(p)
        event.joinMessage(
            plugin.helper.formatConfigMsg("messages.player_joined", "player:" + p.name)
        )
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val p = event.player
        plugin.playerManager.savePlayer(p)
        event.quitMessage(
            plugin.helper.formatConfigMsg("messages.player_left", "player:" + p.name)
        )
    }
}