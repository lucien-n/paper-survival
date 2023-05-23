package me.scaffus.survival.listener

import me.scaffus.survival.Survival

class ListenerManager(private val plugin: Survival) {
    private val listeners: MutableMap<String, SListener> = mutableMapOf()

    fun register() {
        plugin.logger.info("Registering commands")
        val listenerClasses =
            plugin.helper.getClassesFromPackage("me.scaffus.survival.listener.listeners", SListener::class)
        listenerClasses.values.forEach { listenerClass -> registerListener(listenerClass as SListener) }
    }

    fun registerListener(listener: SListener) {
        if (listeners.values.contains(listener)) return
        listeners[listener.name] = listener
        plugin.logger.info("Registering listener '${listener.name}'")
    }

    fun getListener(name: String): SListener? {
        return listeners[name]
    }

    fun getListeners(): List<SListener> {
        return listeners.values.toList()
    }
}