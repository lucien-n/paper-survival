package me.scaffus.survival.command.commands

import me.scaffus.survival.Survival
import me.scaffus.survival.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MessageCommand(private val plugin: Survival) : Command(plugin, "message") {
    override fun onCommand(
        sender: CommandSender,
        command: org.bukkit.command.Command,
        label: String,
        args: Array<out String>?
    ): Boolean {
        if (sender !is Player) {
            plugin.helper.sendMessage(sender, "error.only_players", "player:${sender.name}")
        }

        if (args == null || args.isEmpty()) {
            plugin.helper.sendMessage(sender as Player, "usage.message")
            return true
        }


        val p = plugin.data.getPlayer(sender as Player) ?: return plugin.helper.sendMessage(sender, "error.error")
            .let { true }

        if (!p.setConversationPeer(args[0])) {
            return plugin.helper.sendMessage(sender, "player.not_found", "player:${args[0]}").let { true }
        }

        val message = args.slice(1 until args.size).joinToString(" ")
        val convPeer = p.getConversationPeer() ?: return plugin.helper.sendMessage(sender, "error.error").let { true }

        // Sender side
        plugin.helper.sendMessage(sender, "message.sent", "receiver:${args[0]}", "message:$message")
        // Receiver side
        plugin.helper.sendMessage(
            convPeer,
            "message.received",
            "sender:${sender.name}",
            "message:$message"
        )

        return true
    }
}