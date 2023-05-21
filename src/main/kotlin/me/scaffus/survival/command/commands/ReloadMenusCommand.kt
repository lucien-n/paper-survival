package me.scaffus.sguis.command.commands

import main.kotlin.me.scaffus.survival.command.Command
import me.scaffus.survival.Survival
import org.bukkit.command.CommandSender
import org.bukkit.command.Command as BukkitCommand

class ReloadMenusCommand(private val plugin: Survival) : Command(plugin, "reloadmenus") {
    override fun onCommand(
        sender: CommandSender,
        command: BukkitCommand,
        label: String,
        args: Array<out String>?
    ): Boolean {
        plugin.menuManager.generateMenusFromConfigs()
        sender.sendMessage("Menus reloaded")

        return true;
    }
}