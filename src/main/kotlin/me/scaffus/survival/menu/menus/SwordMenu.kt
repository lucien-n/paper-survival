package me.scaffus.sguis.menu.menus

import me.scaffus.sguis.menu.Menu
import me.scaffus.sguis.menu.Slot
import me.scaffus.survival.Survival
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class SwordMenu(private val plugin: Survival) :
    Menu(
        plugin,
        "sword",
        MiniMessage.miniMessage().deserialize("<bold><italic><aqua>Diamond Sword"),
        27,
        ItemStack(Material.GRAY_STAINED_GLASS_PANE)
    ) {
    var giveCounter = 0

    init {
        val diamondPickaxe = ItemStack(Material.DIAMOND_SWORD)
        diamondPickaxe.addUnsafeEnchantment(Enchantment.DURABILITY, 255)
        setSlot(Slot("sword", 13, diamondPickaxe, { p ->
            p.inventory.addItem(diamondPickaxe)
            p.updateInventory()
            giveCounter++
            updateSlotLoreLine(
                "sword",
                0,
                "<gold>Gave <yellow><amount> <underlined><gold>diamond sword",
                "amount:$giveCounter"
            )
        }))
    }
}