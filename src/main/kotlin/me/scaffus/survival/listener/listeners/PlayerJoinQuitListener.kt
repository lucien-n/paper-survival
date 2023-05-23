package me.scaffus.survival.listener.listeners

import me.scaffus.survival.Survival
import me.scaffus.survival.listener.SListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerJoinQuitListener(private val plugin: Survival) : SListener(plugin, "player_join_quit") {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val p = event.player
        plugin.playerManager.loadPlayer(p)
        event.joinMessage(
            plugin.helper.formatConfigMsg("player.joined", "player:" + p.name)
        )
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val p = event.player
        plugin.playerManager.savePlayer(p)
        event.quitMessage(
            plugin.helper.formatConfigMsg("player.left", "player:" + p.name)
        )
    }
}