package me.scaffus.survival.database

import me.scaffus.survival.Survival
import me.scaffus.survival.player.BankAccount
import org.bukkit.entity.Player
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class DatabaseGetterSetter(private val connection: Connection?, private val plugin: Survival) {
    private fun checkCon() {
        if (connection == null) return
    }

    private fun ps(statement: String): PreparedStatement? {
        checkCon()
        return try {
            connection!!.prepareStatement(statement)
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    fun createPlayer(p: Player) {
        try {
            val uuid = p.uniqueId
            if (!playerExists(uuid)) {
                val psPlayer = ps("INSERT IGNORE INTO players (username, UUID) VALUES (?, ?)")
                psPlayer!!.setString(1, p.name)
                psPlayer.setString(2, uuid.toString())
                psPlayer.executeUpdate()

                val psBank = ps("INSERT IGNORE INTO players_bank (UUID) VALUES (?)")
                psBank!!.setString(1, uuid.toString())
                psBank.executeUpdate()
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    private fun playerExists(uuid: UUID): Boolean {
        return try {
            val preparedStatement = ps("SELECT * FROM players WHERE UUID=?")
            preparedStatement!!.setString(1, uuid.toString())
            val result = preparedStatement.executeQuery()
            result.next()
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    fun loadPlayerBankAccount(uuid: UUID): BankAccount? {
        val s = ps("SELECT * FROM players_bank WHERE UUID=?")
        s!!.setString(1, uuid.toString())
        val result = s.executeQuery()
        if (result.next()) {
            val balance = result.getDouble("balance")
            return BankAccount(uuid, balance)
        }
        return null
    }

    fun savePlayerBankAccount(uuid: UUID, bankAccount: BankAccount) {
        try {
            val s = ps("UPDATE players_bank SET balance=? WHERE UUID=?")
            s!!.setDouble(1, bankAccount.getBalance())
            s.setString(2, uuid.toString())
            s.executeUpdate()
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }
}