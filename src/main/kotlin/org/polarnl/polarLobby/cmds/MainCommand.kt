package org.polarnl.polarLobby.cmds

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.polarnl.polarLobby.PolarLobby
import org.polarnl.polarLobby.util.PlayerMessage

/**
 * MainCommand - Hoofd commando handler voor /pl
 * 
 * Deze klasse beheert alle subcommando's van de plugin.
 * Het routeert commando's naar de juiste SubCommand implementatie
 * en handelt permissie checks en tab completion af.
 * 
 * Beschikbare subcommando's:
 * - reload: Herlaadt de plugin configuratie
 * - allowbreak: Toggle blok plaats/breek permissie voor een speler
 * - setspawn: Stelt de spawn locatie in
 * - voidtp: Configureer void teleportatie settings
 */
class MainCommand(private val plugin: PolarLobby) : CommandExecutor, TabCompleter {
    // Lijst van alle beschikbare subcommando's
    private val subcommands: List<SubCommand> = listOf(
        Reload(plugin),
        AllowBreakCommand(plugin),
        SetSpawn(plugin),
        VoidTP(plugin)
    )

    /**
     * Handelt commando uitvoering af.
     * Zonder argumenten wordt een lijst van beschikbare subcommando's getoond.
     * Met argumenten wordt het opgegeven subcommando uitgevoerd.
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        // Geen argumenten: toon lijst van beschikbare subcommando's
        if (args.isEmpty()) {
            if (sender is Player) {
                if (!sender.hasPermission("polarlobby.admin")) PlayerMessage().send("Powered by PolarLobby. You do not have access to any commands from PolarLobby.", sender)
                PlayerMessage().send("Available subcommands:", sender)
                subcommands
                    .filter {
                        val perm = it.permission
                        perm == null || sender.hasPermission(perm)
                    }
                    .forEach {
                        PlayerMessage().send(
                            "<blue><u><click:suggest_command:\"/pl ${it.name}\">/pl ${it.name}</click></u><green> | ${it.description}", sender
                        )
                    }
            } else {
                sender.sendMessage("Available subcommands: " + subcommands.joinToString(", ") { it.name })
            }
            return true
        }

        // Zoek het opgegeven subcommando
        val key = args[0].lowercase()
        val sub = subcommands.firstOrNull { it.name.equals(key, ignoreCase = true) }
        if (sub == null) {
            // Onbekend subcommando
            if (sender is Player) {
                PlayerMessage().send("<red>Unknown subcommand. See <blue><u><click:suggest_command:\"/pl\">/pl</click></u></blue for available subcommands.", sender)
            } else {
                sender.sendMessage("Unknown subcommand. Use /pl for available subcommands.")
            }
            return true
        }

        // Controleer permissies
        val perm = sub.permission
        if (perm != null && !sender.hasPermission(perm)) {
            if (sender is Player) {
                PlayerMessage().send("<red>You do not have permission to use this command.", sender)
            } else {
                sender.sendMessage("You do not have permission to use this command.")
            }
            return true
        }

        // Voer het subcommando uit
        val subArgs = if (args.size > 1) args.copyOfRange(1, args.size) else emptyArray()
        return sub.execute(sender, label, subArgs)
    }

    /**
     * Handelt tab completion af voor commando suggesties.
     * Suggereert beschikbare subcommando's op basis van permissies.
     */
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        // Tab completion voor eerste argument (subcommando naam)
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

        // Tab completion voor subcommando argumenten
        val sub = subcommands.firstOrNull { it.name.equals(args[0], ignoreCase = true) }
            ?: return mutableListOf()
        val subArgs = args.copyOfRange(1, args.size)
        return sub.tabComplete(sender, subArgs).toMutableList()
    }
}
