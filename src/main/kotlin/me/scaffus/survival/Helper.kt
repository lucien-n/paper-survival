package me.scaffus.survival

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import java.io.File
import kotlin.reflect.KClass


class Helper(private val plugin: Survival) {

    fun getMessageFromConfig(path: String): String {
        return plugin.config.getString(path)!!
    }

    fun getTagResolvers(vararg args: String): Array<TagResolver> {
        val placeholders: MutableList<TagResolver> = ArrayList()
        for (arg in args) {
            placeholders.add(Placeholder.unparsed(arg.split(":")[0], arg.split(":")[1]))
        }
        return placeholders.toTypedArray()
    }

    fun formatConfigMsg(path: String, vararg args: String): Component {
        return MiniMessage.miniMessage().deserialize(
            getMessageFromConfig(path), *getTagResolvers(*args, "prefix:${plugin.data.getPrefix()}")
        )
    }

    fun format(message: String, vararg placeholders: String): Component {
        if (placeholders.isNotEmpty()) {
            return MiniMessage.miniMessage().deserialize(
                message, *getTagResolvers(*placeholders, "prefix:${plugin.data.getPrefix()}")
            )
        }
        return MiniMessage.miniMessage().deserialize(message)
    }

    fun formatLore(lore: Array<String>, vararg placeholders: String): List<Component>? {
        if (lore.isEmpty()) return null

        val formattedLore: MutableList<Component> = ArrayList()
        lore.forEach { line -> formattedLore.add(format(line, *placeholders)) }
        return formattedLore
    }

    fun createItem(
        material: Material,
        amount: Int,
        name: String,
        lore: Array<String>,
        vararg placeholders: String
    ): ItemStack {
        val item = ItemStack(material)

        val itemMeta = item.itemMeta
        itemMeta.displayName(format(name))
        if (lore.isNotEmpty()) {
            itemMeta.lore(formatLore(lore))
        }
        item.itemMeta = itemMeta

        item.amount = amount

        return item
    }

    fun getItemFromConfigSection(config: ConfigurationSection?): ItemStack {
        if (config == null) return ItemStack(Material.AIR)

        val material = Material.getMaterial(config.getString("material") ?: "AIR") ?: Material.AIR
        val amount = (config.get("amount") ?: 1) as Int
        val name = config.getString("name")!!
        val lore = config.getStringList("lore").toTypedArray()

        return createItem(material, amount, name, lore)
    }

    fun getClassesFromPackage(
        packageName: String,
        classType: KClass<*>
    ): MutableMap<String, Any> {
        val classes: MutableMap<String, Any> = mutableMapOf()
        for (anyClass in ClassFinder.getClasses(
            File(plugin.javaClass.protectionDomain.codeSource.location.file),
            packageName
        )) {
            try {
                val classToRegister = Class.forName(anyClass.name)
                if (classType.java.isAssignableFrom(classToRegister)) {
                    val instance = classToRegister.getDeclaredConstructor(Survival::class.java)
                        .newInstance(plugin)
                    classes[classToRegister.name] = instance
                    continue
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return classes
    }

    fun loadConfig(configFile: File): FileConfiguration {
        if (!configFile.exists()) {
            plugin.saveResource(configFile.name, false)
        }

        return YamlConfiguration.loadConfiguration(configFile)
    }
}