package me.scaffus.survival

import main.kotlin.me.scaffus.survival.ClassFinder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.io.File


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

    fun getItemFromConfig(path: String): ItemStack? {
        if (plugin.config.get("$path.material") == null) return null

        val material = Material.getMaterial(plugin.config.getString("$path.material") ?: "AIR") ?: Material.AIR
        val amount = (plugin.config.get("$path.amount") ?: 1) as Int
        val name = plugin.config.getString("$path.name")!!
        val lore = plugin.config.getStringList("$path.lore").toTypedArray()

        return createItem(material, amount, name, lore)
    }

    fun getClassesFromPackage(
        packageName: String,
    ): MutableMap<String, Any> {
        val classes: MutableMap<String, Any> = mutableMapOf()
        for (anyClass in ClassFinder.getClasses(
            File(plugin.javaClass.protectionDomain.codeSource.location.file),
            packageName
        )) {
            try {
                plugin.logger.info("Found class '${anyClass.name}'")
                val classToRegister = Class.forName(anyClass.name)
                plugin.logger.info(
                    "Registering class '${classToRegister.name}'"
                )
                classes[classToRegister.name] =
                    classToRegister.getDeclaredConstructor(Survival::class.java).newInstance(plugin).javaClass
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        plugin.logger.info("Returning ${classes.size} classes")
        return classes
    }
}