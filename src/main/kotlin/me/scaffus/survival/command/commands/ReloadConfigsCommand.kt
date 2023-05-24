package me.scaffus.survival.command.commands

import me.scaffus.survival.Survival
import me.scaffus.survival.command.SCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class ReloadConfigsCommand(private val plugin: Survival) : SCommand(plugin, "reloadconfigs") {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        plugin.menuManager.register()
        plugin.itemManager.register()
        plugin.reloadConfig()
        sender.sendMessage("Configs reloaded")
        return true
    }
}