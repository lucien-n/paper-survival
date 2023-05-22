package me.scaffus.survival.menu.menus

import me.scaffus.survival.Survival
import me.scaffus.survival.menu.Menu
import org.bukkit.entity.Player

class BankMenu(private val plugin: Survival) : Menu(
    plugin,
    "bank"
) {
    init {
        initFromConfig()
        addSlotAction("deposit") { p: Player -> null }
    }
}