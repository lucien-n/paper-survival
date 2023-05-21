package me.scaffus.survival.player

import java.util.*

class BankAccount(private val uuid: UUID, private var balance: Double) {
    fun withdraw(amount: Double): Pair<Boolean, String> {
        if (amount < 0) {
            return Pair(false, "You cannot withdraw a negative amount")
        }

        if (balance < amount) {
            return Pair(false, "Insufficient funds")
        }

        balance -= amount

        return Pair(true, "Withdraw successful")
    }

    fun deposit(amount: Double): Pair<Boolean, String> {
        if (amount < 0) {
            return Pair(false, "You cannot deposit a negative amount")
        }

        balance += amount

        return Pair(true, "Deposit successful")
    }

    fun getBalance(): Double {
        return balance
    }
}