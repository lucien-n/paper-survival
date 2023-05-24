package me.scaffus.survival.command.commands

import me.scaffus.survival.Survival
import me.scaffus.survival.command.SCommand
import org.bukkit.command.CommandSender

class ListMenusCommand(private val plugin: Survival) : SCommand(plugin, "listmenus") {
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