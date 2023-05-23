package me.scaffus.survival.menu

import me.scaffus.survival.Survival
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

class GeneratedSMenu(
    private val plugin: Survival,
    val menuName: String,
    val title: Component,
    val size: Int,
    backgroundItem: ItemStack? = null
) :
    SMenu(plugin, menuName, title, size, backgroundItem) {
}