package org.polarnl.polarLobby.cmds

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.polarnl.polarLobby.PolarLobby
import org.polarnl.polarLobby.util.PlayerMessage
import org.polarnl.polarLobby.util.ServerConsole

/**
 * AllowBreakCommand - Toggle blok plaats/breek permissie
 * 
 * Dit commando geeft admins de mogelijkheid om individuele spelers
 * toestemming te geven om blokken te plaatsen en breken in de lobby.
 * Dit is handig wanneer iemand aan de lobby moet bouwen terwijl
 * de server online is.
 * 
 * Het commando werkt als een toggle - uitvoeren op een speler die
 * al toestemming heeft, verwijdert de toestemming weer.
 */
class AllowBreakCommand(private val plugin: PolarLobby) : SubCommand {
    override val name: String = "allowbreak"
    override val description: String = "Allows you (or someone else) to break/place blocks in the lobby"
    override val permission: String = "polarlobby.admin"
    override val usage: String = "/pl allowbreak <player>"

    /**
     * Toggle blok plaats/breek permissie voor een speler.
     * Zonder argumenten wordt de permissie voor de uitvoerende speler getoggled.
     * Met een speler naam als argument wordt de permissie voor die speler getoggled.
     */
    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        // Bepaal wie de target speler is
        val target: Player? = when {
            args.isNotEmpty() -> plugin.server.getPlayerExact(args[0])
            sender is Player -> sender
            else -> null
        }

        if (target == null) {
            if (sender is Player) {
                PlayerMessage().send("<red>That player is not online.", sender)
            } else {
                ServerConsole.error("That player is not online.")
            }
            return true
        }

        // Toggle de permissie - voeg toe of verwijder uit de toegestane lijst
        val allowManager = plugin.allowBlockBreak
        val nowAllowed = if (allowManager.allowedPlayers.contains(target.uniqueId)) {
            allowManager.allowedPlayers.remove(target.uniqueId)
            false
        } else {
            allowManager.allowedPlayers.add(target.uniqueId)
            true
        }

        // Stuur feedback berichten
        val statusText = (if (sender == target) "You" else target.name) + (if (nowAllowed) " <green>can now" else " <red>can no longer")
        if (sender is Player) {
            PlayerMessage().send("$statusText <white>break/place blocks in the lobby.", sender)
            // Informeer ook de target speler als het niet dezelfde is
            if (sender.uniqueId != target.uniqueId) {
                PlayerMessage().send("$statusText <white>break/place blocks in the lobby.", target)
            }
        } else {
            ServerConsole.info("${target.name} ${if (nowAllowed) "can now" else "can no longer"} break/place blocks in the lobby.")
            PlayerMessage().send("$statusText <white>break/place blocks in the lobby.", target)
        }

        return true
    }
    
    /**
     * Tab completion voor speler namen.
     */
    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        if (args.isEmpty()) return emptyList()
        val prefix = args[0].lowercase()
        return plugin.server.onlinePlayers
            .map { it.name }
            .filter { it.lowercase().startsWith(prefix) }
            .sorted()
    }
}