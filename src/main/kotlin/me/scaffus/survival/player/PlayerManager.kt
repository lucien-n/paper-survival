package me.scaffus.survival.player

import me.scaffus.survival.Survival
import org.bukkit.Bukkit
import java.util.*
import org.bukkit.entity.Player as BukkitPlayer

class PlayerManager(private val plugin: Survival) {
    fun loadPlayer(bukkitPlayer: BukkitPlayer) {
        val uuid = bukkitPlayer.uniqueId
        val bankAccount = loadBankAccount(uuid)
        plugin.data.setPlayer(uuid, Player(uuid, bankAccount))
    }

    fun loadOnlinePlayers() {
        for (p: BukkitPlayer in Bukkit.getOnlinePlayers()) {
            loadPlayer(p)
        }
    }

    private fun loadBankAccount(uuid: UUID): BankAccount {
        var bankAccount = plugin.db.loadPlayerBankAccount(uuid)
        if (bankAccount == null) {
            plugin.logger.info("$uuid doesn't have a bank account")
            bankAccount = BankAccount(uuid, 0.0)
        }
        return bankAccount
    }

    fun savePlayer(bukkitPlayer: BukkitPlayer) {
        val uuid = bukkitPlayer.uniqueId
        val player = plugin.data.getPlayer(uuid)
        if (player == null) {
            plugin.logger.info("Error while saving $uuid : Player not found")
            return
        }
        plugin.db.savePlayerBankAccount(uuid, player.bankAccount)
    }
}