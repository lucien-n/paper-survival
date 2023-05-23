package me.scaffus.survival.command.commands

import me.scaffus.survival.command.SCommand
import me.scaffus.survival.Survival
import org.bukkit.command.CommandSender

class ListMenusCommand(private val plugin: Survival) : SCommand(plugin, "listmenus") {
    override fun onCommand(
        sender: CommandSender,
        command: org.bukkit.command.Command,
        label: String,
        args: Array<out String>?
    ): Boolean {
        sender.sendMessage("Menus: ${plugin.getSMenus().joinToString(", ") { it.name }}")
        return true
    }
}