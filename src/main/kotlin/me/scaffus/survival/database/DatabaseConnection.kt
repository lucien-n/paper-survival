package me.scaffus.survival.database

import me.scaffus.survival.Survival
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DatabaseConnection(private val plugin: Survival, private val databaseCredentials: DatabaseCredentials) {
    var connection: Connection? = null

    init {
        connect()
        plugin.logger.info("Connection to database successful")
    }

    private fun connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            connection = DriverManager.getConnection(
                databaseCredentials.toUri(),
                databaseCredentials.user,
                databaseCredentials.password
            )
        } catch (e: SQLException) {
            throw RuntimeException(e)
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        }
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