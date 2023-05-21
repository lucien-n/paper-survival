package me.scaffus.survival

import me.scaffus.survival.player.BankAccount
import me.scaffus.survival.player.Player
import java.util.*

class Data(private val plugin: Survival) {
    private var prefix: String? = null
    private val players = HashMap<UUID, Player>()

    init {
        prefix = plugin.config.getString("prefix")
    }

    fun getPlayer(uuid: UUID): Player? {
        return players[uuid]
    }

    fun getBankAccount(uuid: UUID): BankAccount {
        return players[uuid]!!.bankAccount
    }

    fun setPlayer(uuid: UUID, player: Player) {
        players[uuid] = player
    }

    fun getPrefix(): String? {
        return prefix
    }
}