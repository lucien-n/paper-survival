package me.scaffus.survival

import me.scaffus.survival.player.BankAccount
import me.scaffus.survival.player.SPlayer
import org.bukkit.entity.Player
import java.util.*

class Data(private val plugin: Survival) {
    private var prefix: String? = null
    private val players = HashMap<UUID, SPlayer>()

    init {
        prefix = plugin.config.getString("prefix")
    }

    fun getPlayer(uuid: UUID): SPlayer? {
        return players[uuid]
    }

    fun getPlayer(p: Player): SPlayer? {
        return players[p.uniqueId]
    }

    fun getBankAccount(uuid: UUID): BankAccount {
        return players[uuid]!!.bankAccount
    }

    fun setPlayer(uuid: UUID, player: SPlayer) {
        players[uuid] = player
    }

    fun getPrefix(): String? {
        return prefix
    }
}