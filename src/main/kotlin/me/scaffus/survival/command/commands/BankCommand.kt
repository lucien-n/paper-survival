package main.kotlin.me.scaffus.survival.command.commands

import me.scaffus.survival.Survival
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BankCommand(private var plugin: Survival) : CommandExecutor {
    init {
        plugin.getCommand("bank")!!.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        plugin.menuManager.getMenuByName("bank")!!.open(sender)

        return true
    }
}