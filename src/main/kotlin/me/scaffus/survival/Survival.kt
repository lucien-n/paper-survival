package me.scaffus.survival

import me.scaffus.survival.command.CommandManager
import me.scaffus.survival.command.SCommand
import me.scaffus.survival.database.DatabaseGetterSetter
import me.scaffus.survival.database.DatabaseManager
import me.scaffus.survival.listener.ListenerManager
import me.scaffus.survival.listener.SListener
import me.scaffus.survival.menu.MenuManager
import me.scaffus.survival.menu.SMenu
import me.scaffus.survival.player.PlayerManager
import me.scaffus.survival.player.SPlayer
import org.bukkit.command.Command
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.sql.SQLException
import java.util.UUID

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
        logger.info("Plugin enabled")

        dbManager = DatabaseManager(this)
        db = DatabaseGetterSetter(dbManager.playerCon.connection, this)
        data = Data(this)
        helper = Helper(this)
        playerManager = PlayerManager(this)
        menuManager = MenuManager(this)
        commandManager = CommandManager(this)
        listenerManager = ListenerManager(this)

        saveDefaultConfig()

        playerManager.loadOnlinePlayers()
        commandManager.register()
        listenerManager.register()
        menuManager.register()
    }

    override fun onDisable() {
        logger.info("Plugin disabled")
        try {
            dbManager.close()
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    fun getSCommand(name: String): SCommand? {
        return commandManager.getCommand(name)
    }

    fun getSListener(name: String): SListener? {
        return listenerManager.getListener(name)
    }

    fun getSListeners(): List<SListener> {
        return listenerManager.getListeners()
    }

    fun getSMenu(name: String): SMenu? {
        return menuManager.getMenu(name)
    }

    fun getSMenus(): List<SMenu> {
        return menuManager.getMenus().values.toList()
    }

    fun getSPlayer(name: String): SPlayer? {
        return playerManager.getPlayer(name)
    }

    fun getSPlayer(uuid: UUID): SPlayer? {
        return playerManager.getPlayer(uuid)
    }

    fun getSPlayer(p: Player): SPlayer? {
        return playerManager.getPlayer(p)
    }

    fun getSPlayers(): List<SPlayer> {
        return playerManager.getPlayers()
    }
}