package me.scaffus.survival.menu.menus

import me.scaffus.survival.Survival
import me.scaffus.survival.menu.SMenu
import org.bukkit.entity.Player

class BankSMenu(private val plugin: Survival) : SMenu(
    plugin,
    "bank"
) {
    init {
        initFromConfig()
        addSlotAction("deposit") { p: Player -> null }
    }
}