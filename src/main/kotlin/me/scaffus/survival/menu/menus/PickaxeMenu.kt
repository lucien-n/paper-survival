package me.scaffus.sguis.menu.menus

import me.scaffus.sguis.menu.Menu
import me.scaffus.sguis.menu.Slot
import me.scaffus.survival.Survival
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.entity.Damageable
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class PickaxeMenu(private val plugin: Survival) :
    Menu(
        plugin,
        "pickaxe",
        MiniMessage.miniMessage().deserialize("<bold><italic><aqua>Diamond Pickaxe"),
        27,
        ItemStack(Material.GRAY_STAINED_GLASS_PANE)
    ) {
    var giveCounter = 0

    init {
        val diamondPickaxe = ItemStack(Material.DIAMOND_PICKAXE)

        val durability = diamondPickaxe.itemMeta as Damageable
        durability.damage(durability.health - 1)
        diamondPickaxe.itemMeta = durability as ItemMeta

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