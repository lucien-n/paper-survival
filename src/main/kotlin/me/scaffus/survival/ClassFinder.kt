package me.scaffus.survival

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
                if (name.startsWith(packageName!!) && name.endsWith(".class")) classes.add(
                    Class.forName(
                        name.substring(
                            0, name.length - 6
                        )
                    )
                )
            }
            file.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return classes
    }
}