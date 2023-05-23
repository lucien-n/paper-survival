package me.scaffus.survival.menu

import me.scaffus.survival.player.SPlayer
import org.bukkit.inventory.ItemStack

class Slot(val name: String, var slots: List<Int>, var item: ItemStack, vararg actions: (p: SPlayer) -> Unit) {
    var actions: MutableList<(p: SPlayer) -> Unit> = mutableListOf(*actions)

    constructor(name: String, slot: Int, item: ItemStack, vararg actions: (p: SPlayer) -> Unit) : this(
        name,
        listOf(slot),
        item,
        *actions
    )

    fun invokeActions(p: SPlayer) {
        actions.forEach { action -> action.invoke(p) }
    }
}
