package me.scaffus.survival.player

import me.scaffus.survival.SResult
import java.util.*

class BankAccount(private val uuid: UUID, private var balance: Double) {
    fun withdraw(amount: Number): SResult {
        val withdrawAmount = amount.toDouble()

        if (withdrawAmount < 0) {
            return SResult(false, "bank.no_negative")
        }

        if (balance < withdrawAmount) {
            return SResult(false, "bank.insufficient_funds")
        }

        balance -= withdrawAmount

        return SResult(true, "bank.withdraw_successful")
    }

    fun deposit(amount: Number): SResult {
        val depositAmount = amount.toDouble()

        if (depositAmount < 0) {
            return SResult(false, "bank.no_negative")
        }

        balance += depositAmount

        return SResult(true, "bank.deposit_successful")
    }

    fun getBalance(): Double {
        return balance
    }
}