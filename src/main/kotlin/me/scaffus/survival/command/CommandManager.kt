package main.kotlin.me.scaffus.survival.command

import me.scaffus.survival.Survival

class CommandManager(private val plugin: Survival) {
    private val commands: MutableMap<String, Command> = mutableMapOf()

    fun register() {
        plugin.logger.info("Registering commands")
        val commandClasses =
            plugin.helper.getClassesFromPackage("me.scaffus.survival.command.commands")
        commandClasses.forEach { (_, commandClass) -> registerCommand(commandClass as Command) }
    }

    fun registerCommand(command: Command) {
        if (commands.values.contains(command)) return
        commands[command.name] = command
        plugin.logger.info("Registering command '${command.name}'")
    }
}