package me.scaffus.survival.menu.menus

import me.scaffus.survival.Survival
import me.scaffus.survival.menu.SMenu
import me.scaffus.survival.menu.Slot
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class SwordSMenu(private val plugin: Survival) :
    SMenu(
        plugin,
        "sword",
        MiniMessage.miniMessage().deserialize("<bold><italic><aqua>Diamond Sword"),
        27,
        ItemStack(Material.GRAY_STAINED_GLASS_PANE)
    ) {
    var giveCounter = 0

    init {
        val diamondSword = ItemStack(Material.DIAMOND_SWORD)
        diamondSword.addUnsafeEnchantment(Enchantment.DURABILITY, 255)
        setSlot(Slot("sword", 13, diamondSword, { p ->
            p.inventory.addItem(diamondSword)
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