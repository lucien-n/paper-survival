package me.scaffus.survival

import main.kotlin.me.scaffus.survival.command.CommandManager
import main.kotlin.me.scaffus.survival.listener.ListenerManager
import me.scaffus.sguis.menu.MenuManager
import me.scaffus.survival.database.DatabaseGetterSetter
import me.scaffus.survival.database.DatabaseManager
import me.scaffus.survival.player.PlayerManager
import org.bukkit.plugin.java.JavaPlugin
import java.sql.SQLException

class Survival : JavaPlugin() {
    private lateinit var dbManager: DatabaseManager
    lateinit var db: DatabaseGetterSetter
    lateinit var data: Data
    lateinit var helper: Helper
    lateinit var playerManager: PlayerManager
    lateinit var menuManager: MenuManager
    lateinit var commandManager: CommandManager
    lateinit var listenerManager: ListenerManager
    override fun onEnable() {
        dbManager = DatabaseManager(this)
        db = DatabaseGetterSetter(dbManager.playerCon.connection, this)
        data = Data(this)
        helper = Helper(this)
        playerManager = PlayerManager(this)
        menuManager = MenuManager(this)
        commandManager = CommandManager(this)
        listenerManager = ListenerManager(this)

        saveDefaultConfig()
        logger.info("Plugin enabled")

        playerManager.loadOnlinePlayers()
        commandManager.register()
        listenerManager.register()
        menuManager.register()
    }

    override fun onDisable() {
        try {
            dbManager.close()
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }
}