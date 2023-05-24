package me.scaffus.survival.command.commands

import me.scaffus.survival.Survival
import me.scaffus.survival.command.SCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DetailCommand(private val plugin: Survival) : SCommand(plugin, "detail") {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player)
            return plugin.helper.sendMessage(sender, "player_only").let { true }

        val p = plugin.playerManager.getPlayer(sender) ?: return true
        val item = p.player.inventory.itemInMainHand

        if (item.type.isAir)
            return p.sendMessage("You are not holding an item in your hand.").let { true }

        p.sendMessage("Item: ${item.type}")
        p.sendMessage("Lore: ${item.itemMeta.lore}")
        p.sendMessage("Amount: ${item.amount}")
        p.sendMessage("Durability: ${item.durability}")
        p.sendMessage("Data: ${item.data}")

        // Example: Get and display custom item metadata
        val customModelData = item.getItemMeta()?.customModelData
        p.sendMessage("Custom Model Data: $customModelData")

        return true
    }
}