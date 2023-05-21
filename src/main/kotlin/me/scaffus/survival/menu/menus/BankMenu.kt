package main.kotlin.me.scaffus.survival.menu.menus

import me.scaffus.sguis.menu.Menu
import me.scaffus.survival.Survival
import org.bukkit.entity.Player

class BankMenu(private val plugin: Survival) : Menu(
    plugin,
    "bank",
    plugin.helper.formatConfigMsg("menus.bank.name"),
    27,
    plugin.helper.getItemFromConfig("menus.bank.background")
        ?: plugin.helper.getItemFromConfig("menus.default.background")
) {
    init {
        initFromConfig()
        addSlotAction("deposit") { p: Player -> p.sendMessage("deposit") }
    }
}