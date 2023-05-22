package me.scaffus.survival.player

import me.scaffus.survival.Survival
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryView
import java.util.*

abstract class SPlayer(private val plugin: Survival, private val uuid: UUID, val bankAccount: BankAccount) : Player {
    private var currentInventory: InventoryView? = null
    var conversationPeer: SPlayer? = null

    fun getCurrentInventory(): InventoryView? {
        return currentInventory
    }

    fun setCurrentInventory(inventory: InventoryView) {
        currentInventory = inventory
    }

    fun getUuid(): UUID {
        return uuid
    }

    fun getConversationPeer(): SPlayer? {
        return conversationPeer
    }

    fun setConversationPeer(peer: Player): Boolean {
        val peerSPlayer = plugin.data.getPlayer(peer) ?: return false
        conversationPeer = peerSPlayer
        return true;
    }

    fun setConversationPeer(playerName: String): Boolean {
        val player = Bukkit.getPlayer(playerName) ?: return false
        val peerSPlayer = plugin.data.getPlayer(player) ?: return false
        conversationPeer = peerSPlayer
        return true
    }
}