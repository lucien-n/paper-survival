package main.kotlin.me.scaffus.survival

import org.bukkit.Bukkit
import java.io.File
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile


object ClassFinder {
    fun getClasses(jarFile: File?, packageName: String?): Set<Class<*>> {
        val classes: MutableSet<Class<*>> = HashSet()
        try {
            val file = JarFile(jarFile)
            val entry: Enumeration<JarEntry> = file.entries()
            while (entry.hasMoreElements()) {
                val jarEntry: JarEntry = entry.nextElement()
                val name: String = jarEntry.getName().replace("/", ".")
                if (name.startsWith("me.scaffus.survival"))
                    Bukkit.getLogger().info("Found class '$name'")
                if (name.startsWith(packageName!!) && name.endsWith(".class")) classes.add(
                    Class.forName(
                        name.substring(
                            0,
                            name.length - 6
                        )
                    )
                )
            }
            file.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Bukkit.getLogger().info("Returning classes '${classes.size}'")
        return classes
    }
}