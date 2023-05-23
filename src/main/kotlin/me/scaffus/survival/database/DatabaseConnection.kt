package me.scaffus.survival.database

import me.scaffus.survival.Survival
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DatabaseConnection(private val plugin: Survival, private val databaseCredentials: DatabaseCredentials) {
    var connection: Connection? = null

    init {
        val connectionSuccessful = connect()
        if (connectionSuccessful)
            plugin.logger.info("Connection to database successful")
        else
            plugin.logger.warning("Couldn't connect to database")
    }

    private fun connect(): Boolean {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            connection = DriverManager.getConnection(
                databaseCredentials.toUri(),
                databaseCredentials.user,
                databaseCredentials.password
            )
            return true
        } catch (e: SQLException) {
            plugin.logger.severe(e.toString())
        } catch (e: ClassNotFoundException) {
            plugin.logger.severe(e.toString())
        }
        return false
    }

    @Throws(SQLException::class)
    fun close() {
        if (connection != null && !connection!!.isClosed) {
            connection!!.close()
        }
    }

    /*    fun getConnection(): Connection? {
            try {
                if (connection != null && !connection!!.isClosed) {
                    return connection
                }
            } catch (e: SQLException) {
                throw RuntimeException(e)
            }
            connect()
            return connection
        }*/
}