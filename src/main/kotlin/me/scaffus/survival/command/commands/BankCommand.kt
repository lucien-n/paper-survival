package me.scaffus.survival.command.commands

import me.scaffus.survival.Survival
import me.scaffus.survival.command.SCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BankCommand(private var plugin: Survival) : SCommand(plugin, "bank") {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return true

        val p = plugin.playerManager.getPlayer(sender) ?: return true

        val menu =
            plugin.menuManager.getMenu("bank") ?: return p.sendMessage("menu.not_found", "menu:bank").let { true }
        menu.open(p)

        return true
    }
}