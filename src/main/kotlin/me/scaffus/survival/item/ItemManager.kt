package me.scaffus.survival.item

import me.scaffus.survival.Survival
import java.io.File
import java.io.IOException

class ItemManager(private val plugin: Survival) {
    private val items: MutableMap<String, SItem> = mutableMapOf()

    fun register() {
        items.clear()
        plugin.logger.info("Registering items")
        val itemClasses = plugin.helper.getClassesFromPackage("me.scaffus.survival.item.items", SItem::class)
        itemClasses.values.forEach { itemClass -> registerItem(itemClass as SItem) }

        plugin.logger.info("Registering items from config")
        generateItemsFromConfigs()
    }

    private fun registerItem(item: SItem) {
        if (items.values.contains(item)) return
        items[item.name] = item
        plugin.logger.info("Registering item '${item.name}'")
    }

    fun get(itemName: String): SItem? {
        return items[itemName]
    }

    fun getItems(): MutableMap<String, SItem> {
        return items
    }

    fun getItemFromConfig(itemConfigFile: File) {
        val itemName = itemConfigFile.name.replace(".yml", "")
        if (items.keys.contains(itemName)) return

        val config = plugin.helper.loadConfig(itemConfigFile)
        val itemStack = plugin.helper.getItemFromConfigSection(config)

        val flags = config.getStringList("flags")

        items[itemName] = SItem(plugin, itemName, itemStack, flags)
    }

    fun generateItemsFromConfigs() {
        val itemsDir = File(plugin.dataFolder, "items")
        if (!itemsDir.exists() || !itemsDir.isDirectory) {
            if (!itemsDir.mkdir()) {
                plugin.logger.warning("Failed to create directory 'items'")
                return
            }

            val defaultConfigString = "title: \"<bold>Custom Item\"\n" +
                    "material: DIAMOND\n" +
                    "lore:\n" +
                    "  - \"<italic>Custom Item\"\n".trimIndent()
            val defaultConfigFile = File(plugin.dataFolder, "items/example.yml")
            try {
                defaultConfigFile.writeText(defaultConfigString)
            } catch (e: IOException) {
                plugin.logger.warning("Failed to create default item config file")
            }
            return
        }

        val itemsConfigFiles = itemsDir.listFiles { _, name -> name.endsWith(".yml") } ?: return
        if (itemsConfigFiles.isEmpty()) {
            plugin.logger.warning("No item config found!")
            return
        }

        for (menuConfigFile in itemsConfigFiles) {
            getItemFromConfig(menuConfigFile)
        }
    }
}