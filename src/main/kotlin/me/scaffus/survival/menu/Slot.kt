package me.scaffus.survival.menu

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Slot(val name: String, var slots: List<Int>, var item: ItemStack, vararg actions: (p: Player) -> Unit) {
    var actions: MutableList<(p: Player) -> Unit> = mutableListOf(*actions)

    constructor(name: String, slot: Int, item: ItemStack, vararg actions: (p: Player) -> Unit) : this(
        name,
        listOf(slot),
        item,
        *actions
    )

    fun invokeActions(p: Player) {
        actions.forEach { action -> action.invoke(p) }
    }
}
