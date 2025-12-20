package org.polarnl.polarLobby.cmds

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.polarnl.polarLobby.PolarLobby
import org.polarnl.polarLobby.util.PlayerMessage

class MainCommand(private val plugin: PolarLobby) : CommandExecutor, TabCompleter {
    private val subcommands: List<SubCommand> = listOf(
        Reload(plugin)
    )

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            PlayerMessage().send("Available subcommands:", sender as Player)
            subcommands
                .filter {
                    val perm = it.permission
                    perm == null || sender.hasPermission(perm)
                }
                .forEach {
                    PlayerMessage().send(
                        "<green><click:suggest_command:\"/pl ${it.name}\">/pl ${it.name}</click> | ${it.description}", sender
                    )
                }
            return true
        }

        val key = args[0].lowercase()
        val sub = subcommands.firstOrNull { it.name.equals(key, ignoreCase = true) }
        if (sub == null) {
            sender.sendMessage(
                "<red>Unknown subcommand. See <blue><u><click:suggest_command:\"/pl\">/pl</click></u></blue for available subcommands."
            )
            return true
        }

        val perm = sub.permission
        if (perm != null && !sender.hasPermission(perm)) {
            PlayerMessage().send("<red>You do not have permission to use this command.", sender as Player)
            return true
        }

        val subArgs = if (args.size > 1) args.copyOfRange(1, args.size) else emptyArray()
        return sub.execute(sender, label, subArgs)
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        if (args.isEmpty() || args.size == 1) {
            val prefix = if (args.isEmpty()) "" else args[0].lowercase()
            return subcommands
                .filter {
                    val perm = it.permission
                    perm == null || sender.hasPermission(perm)
                }
                .map { it.name }
                .filter { it.startsWith(prefix) }
                .toMutableList()
        }

        return mutableListOf()
    }
}
