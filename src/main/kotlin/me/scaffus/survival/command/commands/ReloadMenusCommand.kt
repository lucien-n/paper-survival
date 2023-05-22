package me.scaffus.survival.command.commands

import me.scaffus.survival.Survival
import me.scaffus.survival.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.Command as BukkitCommand

class ReloadMenusCommand(private val plugin: Survival) : Command(plugin, "reloadmenus") {
    override fun onCommand(
        sender: CommandSender,
        command: BukkitCommand,
        label: String,
        args: Array<out String>?
    ): Boolean {
        plugin.menuManager.register()
        sender.sendMessage("Menus reloaded")

        return true;
    }
}