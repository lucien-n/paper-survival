package me.scaffus.survival.item

import me.scaffus.survival.Survival
import org.bukkit.inventory.ItemStack

class SItem(private val plugin: Survival, val name: String, itemStack: ItemStack) : ItemStack(itemStack)