package me.scaffus.survival.player

import me.scaffus.survival.Survival
import org.bukkit.Bukkit
import java.util.*
import org.bukkit.entity.Player

class PlayerManager(private val plugin: Survival) {
    private val players = HashMap<UUID, SPlayer>()

    fun loadPlayer(p: Player) {
        val uuid = p.uniqueId
        plugin.db.createPlayer(p)
        val bankAccount = loadBankAccount(uuid)
        setPlayer(uuid, SPlayer(plugin, p, bankAccount))
    }

    fun loadOnlinePlayers() {
        for (p: Player in Bukkit.getOnlinePlayers()) {
            loadPlayer(p)
        }
    }

    private fun loadBankAccount(uuid: UUID): BankAccount {
        val bankAccount = BankAccount(uuid, 0.0)
        return plugin.db.loadPlayerBankAccount(uuid) ?: bankAccount
    }

    fun savePlayer(bukkitPlayer: Player) {
        val uuid = bukkitPlayer.uniqueId
        val player = getPlayer(uuid)
        if (player == null) {
            plugin.logger.info("Error while saving $uuid : Player not found")
            return
        }
        plugin.db.savePlayerBankAccount(uuid, player.bank)
    }

    fun getPlayer(name: String): SPlayer? {
        return getPlayer(Bukkit.getPlayer(name) ?: return null)
    }

    fun getPlayer(uuid: UUID): SPlayer? {
        return players[uuid]
    }

    fun getPlayer(p: Player): SPlayer? {
        return players[p.uniqueId]
    }

    fun getPlayers(): List<SPlayer> {
        return players.values.toList()
    }

    fun getBankAccount(uuid: UUID): BankAccount {
        return players[uuid]!!.bank
    }

    fun setPlayer(uuid: UUID, player: SPlayer) {
        players[uuid] = player
    }

}