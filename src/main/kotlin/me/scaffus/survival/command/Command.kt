package main.kotlin.me.scaffus.survival.command

import me.scaffus.survival.Survival
import org.bukkit.command.CommandExecutor

abstract class Command(private val plugin: Survival, val name: String) : CommandExecutor {
    init {
        plugin.getCommand(name)!!.setExecutor(this)
    }
}