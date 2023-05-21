package me.scaffus.survival.player

import org.bukkit.inventory.InventoryView
import java.util.*

data class Player(private val uuid: UUID, val bankAccount: BankAccount) {
    private var currentInventory: InventoryView? = null

    fun getCurrentInventory(): InventoryView? {
        return currentInventory
    }

    fun setCurrentInventory(inventory: InventoryView) {
        currentInventory = inventory
    }

    fun getUuid(): UUID {
        return uuid
    }
}