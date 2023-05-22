package me.scaffus.survival.menu

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Slot(val name: String, var slot: Int, var item: ItemStack, vararg actions: (p: Player) -> Unit) {
    var actions: MutableList<(p: Player) -> Unit> = mutableListOf(*actions)
}
