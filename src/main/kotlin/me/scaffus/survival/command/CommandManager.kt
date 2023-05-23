package me.scaffus.survival.command

import me.scaffus.survival.Survival
import org.bukkit.command.Command

class CommandManager(private val plugin: Survival) {
    private val commands: MutableMap<String, SCommand> = mutableMapOf()

    fun register() {
        plugin.logger.info("Registering commands")
        val SCommandClasses =
            plugin.helper.getClassesFromPackage("me.scaffus.survival.command.commands", SCommand::class)
        SCommandClasses.values.forEach { commandClass -> registerCommand(commandClass as SCommand) }
    }

    fun registerCommand(SCommand: SCommand) {
        if (commands.values.contains(SCommand)) return
        commands[SCommand.name] = SCommand
        plugin.logger.info("Registering command '${SCommand.name}'")
    }

    fun getCommand(name: String): SCommand? {
        return commands[name]
    }
}