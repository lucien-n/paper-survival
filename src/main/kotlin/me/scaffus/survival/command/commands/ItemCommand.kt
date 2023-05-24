package me.scaffus.survival.command.commands

import me.scaffus.survival.Survival
import me.scaffus.survival.command.SCommand
import net.kyori.adventure.text.Component
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ItemCommand(private val plugin: Survival) : SCommand(plugin, "item") {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player)
            return plugin.helper.sendMessage(sender, "player_only").let { true }

        val p = plugin.playerManager.getPlayer(sender) ?: return true

        if (args.isNullOrEmpty()) return p.sendMessage(Component.text("Please specify an item name")).let { false }
        val item = plugin.itemManager.get(args[0]) ?: return true
        var amount = item.amount

        if (args.size > 1)
            amount = args[1].toInt()

        item.amount = amount
        p.give(item)

        return true
    }
}