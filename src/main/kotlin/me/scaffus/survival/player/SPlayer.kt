package me.scaffus.survival.player

import me.scaffus.survival.Survival
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import java.util.*

class SPlayer(private val plugin: Survival, val player: Player, val bankAccount: BankAccount) {
    private var currentInventory: InventoryView? = null
    val uniqueId: UUID = player.uniqueId
    private var conversationPeer: SPlayer? = null

    fun getCurrentInventory(): InventoryView? {
        return currentInventory
    }

    fun setCurrentInventory(inventory: InventoryView) {
        currentInventory = inventory
    }

    fun getConversationPeer(): SPlayer? {
        return conversationPeer
    }

    fun setConversationPeer(peer: Player): Boolean {
        val peerSPlayer = plugin.playerManager.getPlayer(peer) ?: return false
        conversationPeer = peerSPlayer
        return true;
    }

    fun setConversationPeer(playerName: String): Boolean {
        val player = Bukkit.getPlayer(playerName) ?: return false
        val peerSPlayer = plugin.playerManager.getPlayer(player) ?: return false
        conversationPeer = peerSPlayer
        return true
    }

    fun sendMessage(message: Component) {
        player.sendMessage(message)
    }

    fun sendMessage(message_path: String, vararg placeholders: String) {
        val message = plugin.helper.formatConfigMsg(message_path, *placeholders)
        player.sendMessage(message)
    }

    fun openInventory(inventory: Inventory) {
        player.openInventory(inventory)
    }
}