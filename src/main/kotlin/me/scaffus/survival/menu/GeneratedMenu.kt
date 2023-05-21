package me.scaffus.sguis.menu

import me.scaffus.survival.Survival
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

class GeneratedMenu(
    private val plugin: Survival,
    val menuName: String,
    val title: Component,
    val size: Int,
    backgroundItem: ItemStack? = null
) :
    Menu(plugin, menuName, title, size, backgroundItem) {
}