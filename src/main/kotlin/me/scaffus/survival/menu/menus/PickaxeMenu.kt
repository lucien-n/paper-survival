package me.scaffus.survival.menu.menus

import me.scaffus.survival.Survival
import me.scaffus.survival.menu.SMenu
import me.scaffus.survival.menu.Slot
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class PickaxeMenu(private val plugin: Survival) :
    SMenu(
        plugin,
        "pickaxe",
        MiniMessage.miniMessage().deserialize("<bold><italic><aqua>Diamond Pickaxe"),
        27,
        ItemStack(Material.GRAY_STAINED_GLASS_PANE)
    ) {
    var giveCounter = 0

    init {
        val diamondPickaxe = ItemStack(Material.DIAMOND_PICKAXE)

        setSlot(Slot("pickaxe", 13, diamondPickaxe, { p ->
            p.inventory.addItem(diamondPickaxe)
            p.updateInventory()
            giveCounter++
            updateSlotLoreLine(
                "pickaxe",
                0,
                "<gold>Gave <yellow><amount> <underline><gold>diamond pickaxe",
                "amount:$giveCounter"
            )
        }))
    }
}