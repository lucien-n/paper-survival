package me.scaffus.survival.menu

import me.scaffus.survival.Survival
import me.scaffus.survival.player.SPlayer
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

abstract class SMenu(
    private val plugin: Survival,
    val name: String,
    private val title: Component = Component.empty(),
    private val size: Int = 27,
    private val backgroundItem: ItemStack? = null
) : Listener {
    protected var inventory: Inventory = Bukkit.createInventory(null, size, title)
    val slots: MutableMap<String, Slot> = mutableMapOf()

    init {
        if (backgroundItem != null) setBackground(backgroundItem)
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    fun open(p: SPlayer) {
        p.openInventory(inventory)
    }

    fun open(p: Player) {
        p.openInventory(inventory)
    }

    private fun recreate(menu: SMenu) {
        inventory = Bukkit.createInventory(null, menu.size, menu.title)
        setBackground(menu.backgroundItem ?: return)
        menu.slots.forEach { (_, slot) ->
            setSlot(slot)
        }
    }

    fun initFromConfig() {
        val menuConfig = plugin.menuManager.getMenuConfig(name)
        menuConfig.let { config ->
            val menu = plugin.menuManager.parseMenuConfig(name, config) ?: return
            recreate(menu)
        }
    }

    fun setSlot(slot: Slot) {
        slots[slot.name] = slot
        slot.slots.forEach { slotIndex ->
            inventory.setItem(slotIndex, slot.item)
        }
    }

    fun getSlot(slotName: String): Slot? {
        return slots[slotName]
    }

    fun getSlot(slotPosition: Int): Slot? {
        return slots.values.find { slot -> slot.slots.contains(slotPosition) }
    }

    fun setSlotItemStack(slotName: String, itemStack: ItemStack) {
        val slot = getSlot(slotName) ?: return
        slot.item = itemStack
        setSlot(slot)
    }

    fun addSlotAction(slotName: String, action: (p: SPlayer) -> Unit) {
        val slot = getSlot(slotName) ?: return
        if (slot.actions.contains(action)) return
        slot.actions.add(action)
        setSlot(slot)
    }

    fun removeSlotAction(slotName: String, action: (p: SPlayer) -> Unit) {
        val slot = getSlot(slotName) ?: return
        if (!slot.actions.contains(action)) return
        slot.actions.remove(action)
        setSlot(slot)
    }

    protected fun updateSlotLore(slotName: String, lore: Array<String>, vararg placeholders: String) {
        val slot = getSlot(slotName) ?: return

        if (lore.isNotEmpty()) {
            val meta = slot.item.itemMeta
            meta.lore(plugin.helper.formatLore(lore))
            slot.item.itemMeta = meta
        }

        setSlot(slot)
    }


    protected fun updateSlotLoreLine(slotName: String, line: Int, lore: String, vararg placeholders: String) {
        val slot = getSlot(slotName) ?: return
        val itemMeta = slot.item.itemMeta

        if (itemMeta.hasLore()) {
            val itemLore = itemMeta.lore()
            itemLore!![line] = plugin.helper.format(lore, *placeholders)
            itemMeta.lore(itemLore)
        } else {
            val emptyLore: MutableList<Component> = mutableListOf()
            for (i in 0 until line) {
                emptyLore.add(Component.text(""))
            }
            emptyLore.add(line, plugin.helper.format(lore, *placeholders))
            itemMeta.lore(emptyLore)
        }

        setSlot(slot)
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory == inventory) {
            event.isCancelled = true
            val slot = event.rawSlot

            if (slot in 0 until size) {
                handleItemClick(event.whoClicked as Player, slot)
            }
        }
    }

    protected fun setBackground(item: ItemStack) {
        for (i in 0 until size) {
            inventory.setItem(i, item)
        }
    }

    protected fun handleItemClick(p: Player, slotPosition: Int) {
        val player = plugin.playerManager.getPlayer(p.uniqueId) ?: return
        getSlot(slotPosition)?.invokeActions(player)
    }
}
