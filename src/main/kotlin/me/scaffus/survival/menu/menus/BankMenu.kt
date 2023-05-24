package me.scaffus.survival.menu.menus

import me.scaffus.survival.Survival
import me.scaffus.survival.menu.SMenu
import me.scaffus.survival.player.SPlayer

class BankMenu(private val plugin: Survival) : SMenu(
    plugin, "bank"
) {
    init {
        initFromConfig()
        addSlotAction("withdraw") { p: SPlayer ->
            val amount = 1
            p.bank.withdraw(amount).let { (success, message) -> p.sendMessage(message, "amount:$amount") }
        }
        addSlotAction("deposit") { p: SPlayer ->
            val amount = 1
            p.bank.deposit(amount).let { (success, message) -> p.sendMessage(message, "amount:$amount") }
        }
    }
}