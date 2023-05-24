package me.scaffus.survival.command.commands

import me.scaffus.survival.Survival
import me.scaffus.survival.command.SCommand
import org.bukkit.command.CommandSender

class ListItemsCommand(private val plugin: Survival) : SCommand(plugin, "listitems") {
    override fun onCommand(
        sender: CommandSender,
        command: org.bukkit.command.Command,
        label: String,
        args: Array<out String>?
    ): Boolean {
        sender.sendMessage("Items: ${plugin.itemManager.getItems().values.joinToString(", ") { it.name }}")
        return true
    }
}