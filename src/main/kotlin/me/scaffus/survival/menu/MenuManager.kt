package me.scaffus.sguis.menu

import main.kotlin.me.scaffus.survival.command.Command
import me.scaffus.survival.Survival
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack
import java.io.File

class MenuManager(private val plugin: Survival) {
    private val menus: MutableMap<String, Menu> = mutableMapOf()

    private val commands: MutableMap<String, Command> = mutableMapOf()

    fun register() {
        plugin.logger.info("Registering menus")
        val menuClasses =
            plugin.helper.getClassesFromPackage("me.scaffus.survival.menu.menus")
        menuClasses.forEach { (_, menuClass) -> registerMenu(menuClass as Menu) }
    }

    fun registerMenu(menu: Menu) {
        if (menus.values.contains(menu)) return
        plugin.logger.info("Registering menu '${menu.name}'")
        menus[menu.name] = menu
    }

    fun unregisterMenus() {
        for (menu in menus.values) {
            HandlerList.unregisterAll(menu)
        }
        menus.clear()
    }

    fun contains(menuName: String): Boolean {
        return menus.containsKey(menuName)
    }

    fun getMenuByName(menuName: String): Menu? {
        if (!contains(menuName)) return null
        return menus[menuName]
    }

    fun generateMenuFromConfig(menuConfigFile: File) {
        val config = loadConfig(menuConfigFile)
        plugin.logger.info("Generating menu '${menuConfigFile.name}'")

        val menuName = menuConfigFile.name.replace(".yml", "")
        val title = config.getString("title") ?: return
        val size = config.getInt("size")

        val backgroundItem = parseItemData(
            config.getString("background.name") ?: return, config.getConfigurationSection("background") ?: return
        )

        val menu = GeneratedMenu(plugin, menuName, plugin.helper.format(title), size, backgroundItem)

        // Initializing slots
        val items = config.getConfigurationSection("items")?.getKeys(false) ?: return
        for (itemName in items) {
            val itemPath = "items.$itemName"

            // Getting either one or multiple slot(s)
            val slotsPositions: List<Int> = when (val slotConfig = config.get("$itemPath.slot")) {
                is Int -> listOf(slotConfig) // If it's an integer, convert it to a list
                is List<*> -> slotConfig.filterIsInstance<Int>() // If it's a list, filter out the integers
                else -> emptyList() // Default to an empty list if it's neither an int nor a list of int
            }

            // Parsing item stack
            val itemStack = parseItemData(itemName, config.getConfigurationSection(itemPath) ?: continue)

            // Parsing slot action
            // Getting either one or multiple slot(s)
            val actions: List<Map<String, Any>> = when (val actionConfig = config.get("$itemPath.action")) {
                is List<*> -> actionConfig.filterIsInstance<Map<String, Any>>()
                is Map<*, *> -> listOf(actionConfig as Map<String, Any>)
                else -> emptyList()
            }

            plugin.logger.info("Actions: $actions")

            for (slotPosition in slotsPositions) {
                val slot = Slot(itemName, slotPosition, itemStack)
                menu.setSlot(slot)
                actions.forEach { actionConfig ->
                    menu.addSlotAction(
                        slot.name, parseItemAction(actionConfig) ?: return
                    )
                }
            }
        }

        registerMenu(menu)
    }

    private fun parseItemData(itemName: String, itemData: ConfigurationSection): ItemStack {
        val material = Material.getMaterial(itemData.getString("material") ?: "STICK") ?: Material.STICK
        val amount = itemData.getInt("amount")
        val lore = itemData.getStringList("lore").toTypedArray()
        return plugin.helper.createItem(material, if (amount == 0) 1 else amount, itemName, lore)
    }

    private fun parseItemAction(action: Map<String, Any>): ((p: Player) -> Unit)? {
        val type = action["type"] as? String

        if (type.isNullOrBlank()) return null

        when (type.uppercase()) {
            "COMMAND" -> {
                val command = action["command"] ?: return null
                return { p: Player ->
                    plugin.server.dispatchCommand(p, command.toString())
                }
            }

            "OPEN_MENU" -> {
                val menu = action["menu"] ?: return null
                return { p: Player ->
                    getMenuByName((menu.toString()).lowercase())?.open(p)
                }
            }

            "CLOSE_MENU" -> {
                return { p: Player ->
                    p.closeInventory()
                }
            }
        }

        return null
    }

    private fun loadConfig(configFile: File): FileConfiguration {
        if (!configFile.exists()) {
            plugin.saveResource(configFile.name, false)
        }

        return YamlConfiguration.loadConfiguration(configFile)
    }

    fun generateMenusFromConfigs() {
        plugin.logger.info("Generating menus from configs")
        val menusDir = File(plugin.dataFolder, "menus")
        if (!menusDir.exists() || !menusDir.isDirectory) {
            plugin.logger.warning("Menus directory not found!")
            return
        }

        val menuConfigFiles = menusDir.listFiles { _, name -> name.endsWith(".yml") } ?: return
        if (menuConfigFiles.isEmpty()) {
            plugin.logger.warning("No menu configs found!")
            return
        }

        for (menuConfigFile in menuConfigFiles) {
            plugin.logger.info("File name: ${menuConfigFile.name}")
            generateMenuFromConfig(menuConfigFile)
        }
    }

    fun getMenuConfig(name: String): ConfigurationSection {
        val menuConfigFile = File(plugin.dataFolder, "menus/$name.yml")
        return loadConfig(menuConfigFile)
    }
}

