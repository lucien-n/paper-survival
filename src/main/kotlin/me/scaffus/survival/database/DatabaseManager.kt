package me.scaffus.survival.database

import me.scaffus.survival.Survival
import java.sql.SQLException

class DatabaseManager(plugin: Survival) {
    var playerCon: DatabaseConnection = DatabaseConnection(
        plugin, DatabaseCredentials(
            plugin.config.getString("database.host")!!,
            plugin.config.getString("database.user")!!,
            plugin.config.getString("database.password")!!,
            plugin.config.getString("database.table")!!,
            plugin.config.getInt("database.port"),
        )
    )

    @Throws(SQLException::class)
    fun close() {
        playerCon.close()
    }
}