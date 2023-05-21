package me.scaffus.sguis.command.commands

import main.kotlin.me.scaffus.survival.command.Command
import me.scaffus.survival.Survival
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.command.Command as BukkitCommand

class OpenMenuCommand(private val plugin: Survival) : Command(plugin, "openmenu") {
    override fun onCommand(
        sender: CommandSender,
        command: BukkitCommand,
        label: String,
        args: Array<out String>?
    ): Boolean {
        if (sender !is Player) {
            sender.sendMessage("This command is only executable by players")
            return false
        }

        if (args == null || args.isEmpty()) {
            sender.sendMessage("Please specify a menu to open.")
            return false
        }

        val menuName = args[0]
        val menu = plugin.menuManager.getMenuByName(menuName)
        if (menu == null) {
            sender.sendMessage("Could not find menu '$menuName'")
            return false
        }

        menu.open(sender)

        return true;
    }
}