package me.scaffus.survival.menu

import me.scaffus.survival.Survival
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack
import java.io.File

class MenuManager(private val plugin: Survival) {
    private val menus: MutableMap<String, SMenu> = mutableMapOf()
    private val defaults: MutableMap<String, ItemStack> = mutableMapOf()

    init {
        copyMenusFromResources()
    }

    fun register() {
        plugin.logger.info("Registering default items")
        defaults.clear()
        registerDefaults()

        plugin.logger.info("Registering menus")
        unregisterMenus()
        val menuClasses = plugin.helper.getClassesFromPackage("me.scaffus.survival.menu.menus", SMenu::class)
        menuClasses.values.forEach { menuClass ->
            registerMenu(menuClass as SMenu)
        }

        plugin.logger.info("Registering menus from configs")
        generateMenusFromConfigs()
    }

    fun registerDefaults() {
        val menusConfig = plugin.helper.loadConfig(File(plugin.dataFolder, "menus.yml"))
        defaults["background"] =
            plugin.helper.getItemFromConfigSection(menusConfig.getConfigurationSection("defaults.background"))
        defaults["close"] =
            plugin.helper.getItemFromConfigSection(menusConfig.getConfigurationSection("defaults.close"))
        defaults["back"] = plugin.helper.getItemFromConfigSection(menusConfig.getConfigurationSection("defaults.back"))
    }

    fun registerMenu(menu: SMenu) {
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

    fun getDefault(name: String): ItemStack? {
        return defaults[name]
    }

    fun getMenus(): MutableMap<String, SMenu> {
        return menus
    }

    fun contains(menuName: String): Boolean {
        return menus.containsKey(menuName)
    }

    fun getMenu(name: String): SMenu? {
        return menus[name]
    }

    fun generateMenuFromConfig(menuConfigFile: File) {
        val menuName = menuConfigFile.name.replace(".yml", "")
        if (menus.keys.contains(menuName)) return

        val config = plugin.helper.loadConfig(menuConfigFile)
        parseMenuConfig(menuName, config)?.let { menu ->
            registerMenu(menu)
        }
    }

    fun parseMenuConfig(menuName: String, config: ConfigurationSection): SMenu? {
        val itemsConfig = config.getConfigurationSection("items") ?: return null
        val menuSlots: MutableList<Slot> = mutableListOf()

        // Loop through each item slot in the configuration
        for (slotName in itemsConfig.getKeys(false)) {
            val itemConfig = itemsConfig.getConfigurationSection(slotName) ?: continue

            // Get the slot(s) for the item
            val slots: List<Int> = when (val slotConfig = itemConfig.get("slot")) {
                is Int -> listOf(slotConfig) // If it's an integer, convert it to a list
                is List<*> -> slotConfig.filterIsInstance<Int>() // If it's a list, filter out the integers
                else -> emptyList() // Default to an empty list if it's neither an int nor a list of int
            }

            // Get the display name, material, amount, and lore for the item
            val displayName = itemConfig.getString("name") ?: "<bold>Display Name"
            val material: Material = Material.getMaterial(itemConfig.getString("material") ?: "STICK") ?: Material.STICK
            val amount: Int = (itemConfig.get("amount") ?: 1) as Int
            val lore: Array<String> = itemConfig.getStringList("lore").toTypedArray()
            val itemStack = plugin.helper.createItem(material, amount, displayName, lore)

            // Parse slot actions
            val actions: List<(p: Player) -> Unit> = parseItemAction(itemConfig)?.let { listOf(it) } ?: emptyList()

            // Apply slot to the menu
            menuSlots.add(Slot(slotName, slots, itemStack, *actions.toTypedArray()))
        }

        // Get menu display name and size
        val menuDisplayName = config.getString("title") ?: "<bold>Menu Title"
        var size = config.getInt("size")
        if (size % 9 != 0) size = 27

        // Get background item
        var background: ItemStack? = null
        if (config.getString("background") == "default") background = plugin.menuManager.getDefault("background")
        else if (config.getConfigurationSection("background") != null) {
            background = plugin.helper.getItemFromConfigSection(config.getConfigurationSection("background"))
        }

        val menu = GeneratedSMenu(plugin, menuName, plugin.helper.format(menuDisplayName), size, background)
        menuSlots.forEach { slot -> menu.setSlot(slot) }

        return menu
    }

    fun parseItemAction(itemConfig: ConfigurationSection): ((p: Player) -> Unit)? {
        val actionConfig = itemConfig.getConfigurationSection("action") ?: return null
        val type = actionConfig.getString("type")

        if (type.isNullOrBlank()) return null

        when (type.uppercase()) {
            "COMMAND" -> {
                val command = actionConfig.getString("command") ?: return null
                return { p: Player ->
                    plugin.server.dispatchCommand(p, command.toString())
                }
            }

            "OPEN_MENU" -> {
                val menu = actionConfig.getString("menu") ?: return null
                return { p: Player ->
                    getMenu((menu.toString()).lowercase())?.open(p)
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

    fun getMenuConfig(name: String): ConfigurationSection {
        val menuConfigFile = File(plugin.dataFolder, "menus/$name.yml")
        return plugin.helper.loadConfig(menuConfigFile)
    }

    fun generateMenusFromConfigs() {
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
            generateMenuFromConfig(menuConfigFile)
        }
    }


    fun copyMenusFromResources() {
        val menusFolder = File(plugin.dataFolder, "menus")
        if (!menusFolder.exists()) {
            menusFolder.mkdirs()
        }

        val resourceFolder = File(plugin.dataFolder, "resources/menus")
        resourceFolder.listFiles()?.forEach { file ->
            val targetFile = File(menusFolder, file.name)
            if (!targetFile.exists()) {
                file.copyTo(targetFile)
            }
        }
    }
}

