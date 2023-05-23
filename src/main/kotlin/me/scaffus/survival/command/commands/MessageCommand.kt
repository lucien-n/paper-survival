package me.scaffus.survival.command.commands

import me.scaffus.survival.Survival
import me.scaffus.survival.command.SCommand
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MessageCommand(private val plugin: Survival) : SCommand(plugin, "message") {
    override fun onCommand(
        sender: CommandSender,
        command: org.bukkit.command.Command,
        label: String,
        args: Array<out String>?
    ): Boolean {
        if (sender !is Player) {
            plugin.helper.sendMessage(sender, "error.only_players", "player:${sender.name}")
            return true
        }

        if (args == null || args.isEmpty()) {
            plugin.helper.sendMessage(sender, "usage.message")
            return true
        }


        val p = plugin.playerManager.getPlayer(sender) ?: return plugin.helper.sendMessage(sender, "error.error")
            .let { true }

        val receiverFound = p.setConversationPeer(args[0])
        if (!receiverFound)
            return plugin.helper.sendMessage(sender, "player.not_found", "player:${args[0]}").let { true }

        val message = args.slice(1 until args.size).joinToString(" ")

        // Sender side
        plugin.helper.sendMessage(sender, "message.sent", "receiver:${args[0]}", "message:$message")
        // Receiver side
        p.getConversationPeer()?.sendMessage(
            "message.received",
            "sender:${sender.name}",
            "message:$message"
        )

        return true
    }
}