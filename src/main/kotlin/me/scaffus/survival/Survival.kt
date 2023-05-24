package me.scaffus.survival

import me.scaffus.survival.command.CommandManager
import me.scaffus.survival.database.DatabaseGetterSetter
import me.scaffus.survival.database.DatabaseManager
import me.scaffus.survival.item.ItemManager
import me.scaffus.survival.listener.ListenerManager
import me.scaffus.survival.menu.MenuManager
import me.scaffus.survival.player.PlayerManager
import org.bukkit.plugin.java.JavaPlugin
import java.sql.SQLException

class Survival : JavaPlugin() {
    private lateinit var dbManager: DatabaseManager
    lateinit var db: DatabaseGetterSetter
    lateinit var data: Data
    lateinit var helper: Helper

    lateinit var playerManager: PlayerManager
    lateinit var commandManager: CommandManager
    lateinit var listenerManager: ListenerManager
    lateinit var itemManager: ItemManager
    lateinit var menuManager: MenuManager

    override fun onEnable() {
        logger.info("Plugin enabled")

        dbManager = DatabaseManager(this)
        db = DatabaseGetterSetter(dbManager.playerCon.connection, this)

        data = Data(this)
        helper = Helper(this)

        playerManager = PlayerManager(this)
        commandManager = CommandManager(this)
        listenerManager = ListenerManager(this)
        itemManager = ItemManager(this)
        menuManager = MenuManager(this)

        playerManager.loadOnlinePlayers()
        commandManager.register()
        listenerManager.register()
        itemManager.register()
        menuManager.register()

        saveDefaultConfig()
    }

    override fun onDisable() {
        logger.info("Plugin disabled")
        try {
            dbManager.close()
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }
}