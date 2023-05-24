package me.scaffus.survival.item

import me.scaffus.survival.Survival
import org.bukkit.inventory.ItemStack

class SItem(
    private val plugin: Survival,
    val name: String,
    itemStack: ItemStack,
    val flags: MutableSet<String> = mutableSetOf()
) :
    ItemStack(itemStack) {
    fun hasFlag(flag: String): Boolean {
        return flags.contains(flag) ?: false
    }

    fun addFlag(flag: String) {
        flags.add(flag)
    }

    fun removeFlag(flag: String) {
        flags.remove(flag)
    }
}