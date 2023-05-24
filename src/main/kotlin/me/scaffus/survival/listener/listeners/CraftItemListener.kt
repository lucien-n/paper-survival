package me.scaffus.survival.listener.listeners

import me.scaffus.survival.Survival
import me.scaffus.survival.listener.SListener
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.CraftItemEvent

class CraftItemListener(private val plugin: Survival) : SListener(plugin, "crafting") {
    @EventHandler
    fun onCraftItem(event: CraftItemEvent) {
        val recipe = event.recipe

    }
}