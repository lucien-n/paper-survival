package me.scaffus.survival.command.commands

import me.scaffus.survival.command.Command
import me.scaffus.survival.Survival
import org.bukkit.command.CommandSender

class ListMenusCommand(private val plugin: Survival) : Command(plugin, "listmenus") {
    override fun onCommand(
        sender: CommandSender,
        command: org.bukkit.command.Command,
        label: String,
        args: Array<out String>?
    ): Boolean {
        sender.sendMessage("Menus: ${plugin.menuManager.getMenus().values.joinToString(", ") { it.name }}")
        return true
    }
}