package me.scaffus.survival

import me.scaffus.survival.player.BankAccount
import me.scaffus.survival.player.SPlayer
import org.bukkit.entity.Player
import java.util.*

class Data(private val plugin: Survival) {
    private var prefix: String? = null

    init {
        prefix = plugin.config.getString("prefix")
    }


    fun getPrefix(): String? {
        return prefix
    }
}